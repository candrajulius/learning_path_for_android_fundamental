package com.candra.latihanworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.work.*
import com.candra.latihanworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit
import kotlin.contracts.contract

class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        workManager = WorkManager.getInstance(this)
        binding.btnOneTimeTask.setOnClickListener(this)
        binding.btnPeriodicTask.setOnClickListener(this)
        binding.btnCancelTask.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnOneTimeTask -> startOneTimeTask()
            R.id.btnPeriodicTask -> startPeriodicTask()
            R.id.btnCancelTask -> cancelPeriodicTask()
        }
    }

    private fun startPeriodicTask() {
        binding.textStatus.text = getString(R.string.status)

        /*
        Anda bisa menambahkan data untuk dikirimkan dengan membuat object Data yang berisi data key-value, key yang dipakai di sini yaitu MyWorker.EXTRA_CITY. Setelah itu dikirimkan melalui setInputData.
         */
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY,binding.editCity.text.toString())
            .build()

        /*
        Constraint digunakan untuk memberikan syarat kapan task ini dieksekusi, perhatikan kode di dalam MainActivity
         */
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Ini digunakan untuk menjalankan PeriodicWorkRequest dengan interval 15 menit
        // Anda bisa mengaturnya dengan mengganti parameter kedua dan ketiga
        // Batas minimal interval yang diperbolehkan hanya 15 menit
        val periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java,15,TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.apply {
            enqueue(periodicWorkRequest)
            // Kode dibawah ini digunakan untuk membaca status secara live
            getWorkInfoByIdLiveData(periodicWorkRequest.id)
                .observe(this@MainActivity){ workInfo ->
                    val status = workInfo.state.name
                    binding.textStatus.append("\n $status")
                    binding.btnCancelTask.isEnabled = false

                    if (workInfo.state == WorkInfo.State.ENQUEUED){
                        binding.btnCancelTask.isEnabled = true
                    }
                }
        }
    }

    private fun cancelPeriodicTask(){
        workManager.cancelWorkById(periodicWorkRequest.id)
    }

    private fun startOneTimeTask() {
        binding.textStatus.text = getString(R.string.status)
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY,binding.editCity.text.toString())
            .build()

       val constraints = Constraints.Builder()
           .setRequiredNetworkType(NetworkType.CONNECTED)
           .build()

        // Kode dibawah ini digunakan untuk menjalankan task hanya satu kali saja
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.let {
            it.enqueue(oneTimeWorkRequest)
            it.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                .observe(this@MainActivity) { workInfo ->
                    val status = workInfo.state.name
                    binding.textStatus.append("\n $status")
                }
        }
    }
}
/*
Berikut adalah kondisi yang bisa digunakan di Constraints

setRequiredNetworkType, ketika bernilai CONNECTED berarti dia harus terhubung ke koneksi internet, apa pun jenisnya. Bila kita ingin memasang ketentuan bahwa job hanya akan berjalan ketika perangkat terhubung ke network Wi-fi, maka kita perlu memberikan nilai UNMETERED.
setRequiresDeviceIdle, menentukan apakah task akan dijalankan ketika perangkat dalam keadaan sedang digunakan atau tidak. Secara default, parameter ini bernilai false. Bila kita ingin task dijalankan ketika perangkat dalam kondisi tidak digunakan, maka kita beri nilai true.
setRequiresCharging, menentukan apakah task akan dijalankan ketika baterai sedang diisi atau tidak. Nilai true akan mengindikasikan bahwa task hanya berjalan ketika baterai sedang diisi. Kondisi ini dapat digunakan bila task yang dijalankan akan memakan waktu yang lama.
setRequiresStorageNotLow, menentukan apakah task yang dijalankan membutuhkan ruang storage yang tidak sedikit. Secara default, nilainya bersifat false.
Dan ketentuan lainnya yang bisa kita gunakan.

Work Chaining
Selain menjalankan single task seperti pada latihan, Anda juga bisa membuat task-chaining, baik secara paralel maupun sekuensial.

contoh codenya

workManager
      .beginWith(workA1, workA2, workA3)
      .then(workB)
      .then(workC1, workC2)
      .enqueue()
 */