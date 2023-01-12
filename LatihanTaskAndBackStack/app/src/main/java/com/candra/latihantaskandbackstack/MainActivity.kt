 package com.candra.latihantaskandbackstack

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.candra.latihantaskandbackstack.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionClickButton()

        showNotification(this@MainActivity,getString(R.string.notification_title),getString(R.string.notification_message),110)

    }

     private fun showNotification(context: Context, title: String, message: String, notifId: Int) {
         val CHANNEL_ID = "Channel_1"
         val CHANNEL_NAME = "Navigation channel"

         val notifDetailIntent = Intent(this,DetailActivity::class.java).apply {
             putExtra(DetailActivity.EXTRA_TITLE,title)
             putExtra(DetailActivity.EXTRA_MESSAGE,message)
         }

         val pendingIntent = TaskStackBuilder.create(this) // membuat stack builder dari TaskStackBuilder
             .addParentStack(DetailActivity::class.java) // Memberikan parent back stack
             .addNextIntent(notifDetailIntent) // Memberikan next intent setelah diklik notifikasinya
             .getPendingIntent(110,PendingIntent.FLAG_UPDATE_CURRENT) // Membuat pending intent

         val notificaitonManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context,CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_baseline_email_24)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context,android.R.color.black))
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             val channel = NotificationChannel(CHANNEL_ID
             ,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT).apply {
                 enableVibration(true)
                 vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)
             }
             builder.setChannelId(CHANNEL_ID)

             notificaitonManagerCompat.createNotificationChannel(channel)
         }

         val notification = builder.build()

         notificaitonManagerCompat.notify(notifId,notification)


     }

     private fun setActionClickButton(){
         binding.btnOpenDetail.setOnClickListener {
             val detailIntent = Intent(this@MainActivity,DetailActivity::class.java).apply {
                 putExtra(DetailActivity.EXTRA_TITLE,getString(R.string.detail_title))
                 putExtra(DetailActivity.EXTRA_MESSAGE,getString(R.string.detail_message))
             }
             startActivity(detailIntent)
         }
     }
}

/*
Kesimpulan
Sedangkan, penambahan atribut launchMode pada MainActivity di AndroidManifest.xml dimaksudkan agar MainActivity tidak selalu menciptakan dirinya kembali (recreate) dan akan diarahkan ke instance MainActivity yang telah tercipta di memori.
 */