package com.candra.latihanservice.service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService


class MyIntentService : JobIntentService() {

    companion object{
        private const val JOB_ID = 1000
        internal const val EXTRA_DURATION = "extra_duration"
        private val TAG = MyIntentService::class.java.simpleName

        fun enqueueWork(context: Context,intent: Intent){
            enqueueWork(context,MyIntentService::class.java, JOB_ID,intent)
        }
    }

    /*
    Kode di atas akan dijalankan pada thread terpisah secara asynchronous. Jadi kita tak lagi perlu membuat background thread seperti pada service sebelumnya.

    Terakhir, IntentService tak perlu mematikan dirinya sendiri. Service ini akan berhenti dengan sendirinya ketika sudah selesai menyelesaikan tugasnya.
     */
    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork: Mulai.....")

        val duration = intent.getLongExtra(EXTRA_DURATION,0)
        try {
            Thread.sleep(duration)
            Log.d(TAG, "onHandleWork: Selesai....")
        }catch (e: InterruptedException){
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Destroy....")
    }


}