package com.candra.latihansimulasiprosesasynchronous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)


        btnStart.setOnClickListener {
            // menggunakan Exceutor untuk background proses
//            useExecutorForBackgroundProccess()
            // Menggunakan Coroutine untuk background proses
            useCoroutineForBackgroundProccess(tvStatus)
        }
    }


    private fun useCoroutineForBackgroundProccess(tvStatus: TextView) {
        lifecycleScope.launch(Dispatchers.Default) {
            //simulate process in background thread
            for (i in 0..10) {
                delay(500)
                val percentage = i * 10
                withContext(Dispatchers.Main) {
                    // update ui in main thread
                    if (percentage == 100) {
                        tvStatus.text = getString(R.string.task_completed)
                    } else {
                        tvStatus.text = String.format(getString(R.string.compressing), percentage)
                    }
                }
            }
        }
    }


    private fun useExecutorForBackgroundProccess(tvStatus: TextView) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            try {
                // simulate process compressing
                for (i in 0..100) {
                    Thread.sleep(500)
                    val percentage = i
                    handler.post {
                        if (percentage != 100) {
                            tvStatus.text =
                                String.format(getString(R.string.compressing), percentage)
                        } else {
                            tvStatus.setText(R.string.task_completed)
                        }
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

/*
Kesimpulan
Menggunakan Executor untuk background proses
 1. newSingleThreadExecutor, digunakan jika Anda hanya ingin membuat satu thread saja. Karena hanya ada satu thread saja, apabila ada beberapa request yang berjalan maka request selanjutnya akan dijalankan setelah thread pertama selesai.
 2. newFixedThreadPool(nThreads), digunakan jika Anda ingin membuat banyak thread dengan memasukkan nThread dengan jumlah thread yang ingin Anda buat. Misal newFixedThreadPool(4), maka akan membuat 4 thread yang tetap. Sehingga apabila ada beberapa request yang berjalan, maka akan dibagi ke 4 thread tersebut secara paralel.
 3. newCachedThreadPool, thread akan dibuat sesuai dengan kebutuhan, ia juga akan menggunakan thread sebelumnya jika bisa dipakai. Lalu thread yang sudah tidak dipakai selama 1 menit akan otomatis dihapus dari cache.

 Menggunakan Coroutine untuk background proses
 1. Dispatchers.Default, adalah default dispatcher jika tidak ada Dispatcher yang didefinisikan, cocok untuk fungsi yang membutuhkan proses CPU yang tinggi, seperti parsing 100 data.
 2. Dispatchers.IO, untuk menjalankan fungsi yang berisi read-write data ke Network/Disk, seperti menulis atau mengambil data ke database dan ke server.
 3. Dispatchers.Main, untuk menjalankan fungsi di Main Thread, biasanya digunakan untuk mengupdate View UI.


 Scope ini berfungsi untuk mengontrol seberapa lama coroutine menjalankan tugasnya. Berikut ini adalah beberapa scope yang bisa Anda pakai di Android.
 Berikut Scope tersebut
 1. CoroutineScope: Scope umum yang digunakan untuk untuk melacak coroutine yang sedang berjalan di dalamnya. Coroutine dapat dibatalkan dengan memanggil fungsi cancel() kapan saja.

 2. LifecycleScope: Scope khusus yang digunakan di dalam Activity atau Fragment. Dengan menggunakan scope ini, sistem akan membatalkan coroutine secara otomatis ketika lifecycle masuk ke keadaan mati (onDestroy).

 3. ViewModelScope: Scope yang khusus digunakan di ViewModel. Dengan menggunakan scope ini, sistem akan membatalkan coroutine secara otomatis ketika ViewModel dibersihkan (onCleared).
 */