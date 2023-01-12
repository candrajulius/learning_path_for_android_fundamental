package com.candra.alarmmanager.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.candra.alarmmanager.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmService: BroadcastReceiver()
{
    companion object{
        // Membuat beberapa variabel konstanta penanda(flag) yang akan digunakan
        // di keseluruhan bagian kode
        const val TYPE_ONE_TIME = "OneTimeAlarm"
        const val TYPE_REPEATING = "RepeatingAlarm"
        //==============================================

        // Digunakan untuk menggunakan dua konstanta untuk key
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        //======================================================//

        // Digunakan untuk menggunakan dua konstanta bertipe data integer
        // untuk menentukan notif ID sebagai ID untuk menampilkan notifikasi kepada pengguna
        private const val ID_ONETIME = 100
        private const val ID_REPEATING = 101
        //=====================================================================

        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"
    }

    override fun onReceive(context: Context, intent: Intent) {

        /*
        Ketika kondisi sesuai, maka akan BroadcastReceiver akan running dengan semua proses yang terdapat di dalam metode onReceive().
         */
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        val title = if (type.equals(TYPE_ONE_TIME,ignoreCase = true)) TYPE_ONE_TIME else TYPE_REPEATING
        val notifId = if (type.equals(TYPE_ONE_TIME,ignoreCase = true)) ID_ONETIME else ID_REPEATING

        // Disini berarti terjadi semua proses yang akan terjadi
        // Memanggil notifikasi dengan alarm
        message?.let {
            showAlarmNotification(context,title,message,notifId)
        }

        // Memanggil toast
        showToast(context,title,message)
    }

    private fun showToast(context: Context,title: String,message: String?) {
        Toast.makeText(context, "$title: $message", Toast.LENGTH_SHORT).show()

    }

    fun setOnTimeAlarm(context: Context,type: String,date: String,time: String,message: String){
        // Validasi inputan date dan time terlebih dahulu
        if (isDateInvalid(date, DATE_FORMAT) || isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmService::class.java).apply {
            putExtra(EXTRA_MESSAGE,message)
            putExtra(EXTRA_TYPE,type)
        }
        Log.e("ONE_TIME", "$date $time", )

        val dateArray = date.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()

        // Pada kode dibawah ini dipecah data date dan time untuk mengambil nilai tahun bulan hari jam dan menit
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR,Integer.parseInt(dateArray[0]))
            set(Calendar.MONTH,Integer.parseInt(dateArray[1]) - 1) // ini karena bulan dimulai dari index ke 0
            set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateArray[2]))
            set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]))
            set(Calendar.MINUTE,Integer.parseInt(timeArray[1]))
            set(Calendar.SECOND,0)
        }

        /*
        intent yang dibuat akan dieksekusi ketika waktu alarm sama dengan waktu pada sistem
        Android. Disini komponen PendingIntent akan diberikan kepada BroadcastReceiver
        Yang membedakan alarm satu dengan alarm yang lain adalah pada ID
         */
        val pendingIntent = PendingIntent.getBroadcast(context, ID_ONETIME,intent,PendingIntent.FLAG_IMMUTABLE)

        /*
        memasang alarm yang dibuat dengan tipe RTC_WAKEUP. Tipe alarm ini dapat membangunkan peranti (jika dalam posisi sleep) untuk menjalankan obyek PendingIntent.
         */
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)

        Toast.makeText(context,"One time alarm set up",Toast.LENGTH_SHORT).show()
    }

    private fun isDateInvalid(date: String,format: String): Boolean{
        return try {
            val df = SimpleDateFormat(format,Locale.getDefault())
                df.isLenient = false
                df.parse(date)
                false
        }catch (e: ParseException){
            true
        }
    }

    private fun showAlarmNotification(context: Context,title: String,message: String,notifId: Int){

        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context,channelId)
            .setSmallIcon(R.drawable.ic_baseline_access_time_24)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context,android.R.color.transparent))
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId
            ,channelName,NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId,notification)

    }

    fun setRepeatingAlarm(context: Context,type: String,time: String,message: String){

        // Validasi inputan waktu terlebih dahulu
        if (isDateInvalid(time, TIME_FORMAT)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmService::class.java)
        intent.putExtra(EXTRA_MESSAGE,message)
        intent.putExtra(EXTRA_TYPE,type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]))
            set(Calendar.MINUTE,Integer.parseInt(timeArray[1]))
            set(Calendar.SECOND,0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent,PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)

        Toast.makeText(context,"Repeating alarm set up",Toast.LENGTH_SHORT).show()
    }

    // Gunakan metode ini untuk mengecek apakah alarm tersebut sudah terdaftar di alarm manager
    @SuppressLint("UnspecifiedImmutableFlag")
    fun isAlarmSet(context: Context, type: String): Boolean{
        val intent = Intent(context,AlarmService::class.java)
        val requestCode = if (type.equals(TYPE_ONE_TIME,true)) ID_ONETIME else ID_REPEATING

        return PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_NO_CREATE) != null
    }

    fun cancelAlarm(context: Context,type: String,){
        val alarManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmService::class.java)
        val requestCode = if (type.equals(TYPE_ONE_TIME,ignoreCase = true)) ID_ONETIME else ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_IMMUTABLE)
        pendingIntent.cancel()

        alarManager.cancel(pendingIntent)

       Toast.makeText(context,"Repeating alarm dibatalkan",Toast.LENGTH_SHORT).show()
    }

    /*
    Karena tipe alarm yang dikirimkan adalah TYPE_REPEATING, maka id yang digunakan adalah NOTIF_ID_REPEATING yang bernilai 101. Setelah memperoleh id tersebut, maka kita dapat mengetahui intent-nya. Dan kemudian kita dapat membatalkan alarmnya.
     */

}