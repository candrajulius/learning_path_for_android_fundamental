package com.candra.mybroadcastreceiver

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.candra.mybroadcastreceiver.databinding.ActivityMainBinding
import com.candra.mybroadcastreceiver.receiver.DownlaodService
import com.candra.mybroadcastreceiver.receiver.DownlaodService.Companion.TAG

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Menginisiasi dalam bentuk BroadCastReceiver
    private lateinit var downloadReceiver: BroadcastReceiver

    companion object{
        private const val SMS_REQUEST_CODE = 101

        // ini adalah event yang akan digunakan
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionAllButton()

        setActionButtonDownload()
    }

    private fun setActionAllButton(){
        binding.btnPermission.setOnClickListener {
            PermissionManager.check(this, Manifest.permission.RECEIVE_SMS , SMS_REQUEST_CODE)
        }
        binding.btnDownload.setOnClickListener {
            val downloadServiceIntent = Intent(this, DownlaodService::class.java)
            startService(downloadServiceIntent)
        }
    }

    private fun setActionButtonDownload(){

        // Ketika event tersebut ditangkap maka downloadReceiver dijalankan
        // Metode onReceive akan merespon event yang dijalankan dari broadcast tersebut
        downloadReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, p1: Intent) {
                Log.d(TAG, "Download Selesai.. ")
                Toast.makeText(context,"Download Selesai",Toast.LENGTH_SHORT).show()
            }
        }
        // Menangkap event yang telah dilakukan oleh service
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        // Mendaftarkan aksi dari download receiver dan event yang diterimanya kedalam MainActivity
        registerReceiver(downloadReceiver,downloadIntentFilter)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE){
            when (PackageManager.PERMISSION_GRANTED) {
                grantResults[0] -> Toast.makeText(this,"Sms receiver permission diterima",Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this,"Sms receiver permission ditolak",Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // Ini digunakan untuk mencopot service yang digunakan yang telah didaftarkan
        unregisterReceiver(downloadReceiver)
    }
}

object PermissionManager {
    fun check(activity: Activity,permission: String,requestCode: Int){
        if (ActivityCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, arrayOf(permission),requestCode)
        }
    }
}

/*
Kesimpulan ada 3 poin yang dapat kita terima disini

1. Registrasikan sebuah obyek BroadcastReceiver pada komponen aplikasi seperti Activity dan Fragment dan tentukan action/event apa yang ingin didengar/direspon.
2. Lakukan proses terkait pada metode onReceive() ketika event atau action yang dipantau di-broadcast oleh komponen lain
3. Jangan lupa untuk mencopot pemasangan obyek receiver sebelum komponen tersebut dihancurkan atau dimatikan.
 */