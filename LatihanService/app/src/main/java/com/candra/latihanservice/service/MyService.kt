package com.candra.latihanservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {
    companion object{
        internal val TAG = MyService::class.java.simpleName
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }

    // Metode ini juga akan dijalankan ketika startService dijalankan dari activity
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"Service dijalankan..")

        serviceScope.launch {
            delay(3000)
            // Kode dibawah ini berfungsi untuk memberhentikan atau mematikan MyService
            // Dari sistem android
            stopSelf()
            Log.d(TAG, "Service dihentikan")
        }

        /*
        START_STICKY menandakan bahwa bila service tersebut dimatikan oleh sistem Android karena kekurangan memori, ia akan diciptakan kembali jika sudah ada memori yang bisa digunakan.
         */

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, "onDestroy: ")
    }

    /*
    Kesimpulan
    Kekurangan dari service tipe ini adalah ia tak menyediakan background thread diluar ui thread secara default. Jadi tiada cara lainnya selain membuat thread secara sendiri
     */
}