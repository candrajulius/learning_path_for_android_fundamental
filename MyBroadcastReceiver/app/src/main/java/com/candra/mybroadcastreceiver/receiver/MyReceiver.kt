package com.candra.mybroadcastreceiver.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.candra.mybroadcastreceiver.SmsReceiverActivity

class MyReceiver : BroadcastReceiver() {

    companion object{
        private val TAG = MyReceiver::class.java.simpleName
    }

    // onReceive() receiver akan memproses metadata dari sms masuk
    override fun onReceive(context: Context, intent: Intent) {
        // digunaka untuk menginisiasi bundle nya
        val bundle = intent.extras
        try {
            bundle?.let {
                val pdusObj = it.get("pdus") as Array<*>
                for (aPdusObj in pdusObj){
                    val currentMessage = getIncomingMessage(aPdusObj as Any,bundle)
                    val senderNum = currentMessage.displayOriginatingAddress
                    val senderMessage = currentMessage.displayMessageBody
                    Log.d(TAG, "senderNum: $senderNum; message: $senderMessage")
                    val showIntent = Intent(context,SmsReceiverActivity::class.java).apply {
                        // kode ini digunakan untuk menjalankan activity pada task yang berbeda. Bila
                        // Activity tersebut sudah ada dalam stack, maka ia akan ditampilkan kelayar
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK

                        putExtra(SmsReceiverActivity.EXTRA_SMS_NO,senderNum)
                        putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE,senderMessage)
                    }

                    context.startActivity(showIntent)

                }
            }
        }catch (e: Exception){
            Log.d(TAG, "Exception smsReceiver $e")
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun getIncomingMessage(aObject: Any, bundle: Bundle): SmsMessage {
        val currentSMS: SmsMessage
        val format = bundle.getString("format")
        currentSMS = if (Build.VERSION.SDK_INT >= 23){
            SmsMessage.createFromPdu(aObject as ByteArray,format)
        }else SmsMessage.createFromPdu(aObject as ByteArray)
        return currentSMS
    }
}
/*
Dalam hal ini kita akan memanfaatkan fasilitas yang terdapat pada kelas SmsManager dan SmsMessage
untuk melakukan pemrosesan SMS.
Untuk memperoleh obyek dari kelas SmsMessage, yaitu obyek currentMessage, kita menggunakan metode getIncomingMessage(). Metode ini akan mengembalikan currentMessage berdasarkan OS yang dijalankan oleh perangkat Android. Hal ini perlu dilakukan karena metode SmsMessage.createFromPdu((object); sudah deprecated di peranti dengan OS Marshmallow atau versi setelahnya.
 */