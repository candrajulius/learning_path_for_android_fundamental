package com.candra.mybroadcastreceiver.receiver

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.candra.mybroadcastreceiver.MainActivity

class DownlaodService: JobIntentService()
{
    companion object{
        val TAG: String = DownlaodService::class.java.simpleName
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null){
            enqueueWork(this,this::class.java,101,intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "Download Service dijalankan")
        try{
            Thread.sleep(5000)
        }catch (e: InterruptedException){
            e.printStackTrace()
        }

        // Kita memberikan event pada activity yang ingin kita gunakan
        val notifyFinishIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
        // Disini akan dikirimkan event tesebut kepada actvity yang ingin kita gunakan
        // dengan menggunakan fungsi dari sendBraodcast()
        sendBroadcast(notifyFinishIntent)
    }
}