package com.candra.simplenotif

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    companion object{
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun sendNotif(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://code.cjsflow.com/"))
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        // Menginisiasi notifikascation Manager
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // membuat notificationCompat pada aplikasi
        // Kode ini juga digunakan untuk mengirim notifikasi sesuai dengan Id yang kita berikan. Fungsi id disini nanti juga bisa
        // untuk membatalkan notifikasi yang sudah muncul
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID) // Memiliki parameter 2 yaitu context dan channel ID
            .setSmallIcon(R.drawable.ic_baseline_notifications_24) // Untuk memberikan small icon pada notifikasi
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.ic_baseline_notifications_24)) // Icon yang muncul disebelah kiri dari teks notifikasi
            .setContentTitle(resources.getString(R.string.content_title)) // Judul dari notifikasi
            .setContentText(resources.getString(R.string.content_text)) // Text yang muncul dibawah judul notifikasi
            .setSubText(resources.getString(R.string.subtext)) // text yang akan muncul di bawah konten
            .setAutoCancel(true) // Digunakan untuk menghapus notifikasi setelah ditekan
            .setContentIntent(pendingIntent) // Intent ini digunakan sebabgai action jika notifikasi ditekan

        /*
        Untuk android Oreo keatas perlu menambahkan notification channel
         */

        /*
        Kode ini digunakan untuk memunculkan notifikasi pada OS Oreo keatas,
        Anda harus membuat channel agar notifikasi bisa berjalan
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // create or update
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_NAME
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()

        mNotificationManager.notify(NOTIFICATION_ID,notification)

    }
}