package com.candra.latihanservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.candra.latihanservice.databinding.ActivityMainBinding
import com.candra.latihanservice.service.MyBoundService
import com.candra.latihanservice.service.MyIntentService
import com.candra.latihanservice.service.MyService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mServiceBound = false
    private lateinit var mBoundService: MyBoundService

    private val mServiceConnection = object: ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mServiceBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolBar()
        actionAllButton()
    }

    private fun actionAllButton(){
        with(binding){
            btnStartService.setOnClickListener{
                setActionBtnService()
            }
            btnStartJobIntentService.setOnClickListener {
               setActionJobIntentService()
            }
            btnStartBoundService.setOnClickListener {
                setStartBoundService()
            }
            btnStopBoundService.setOnClickListener {
                setStopBoundService()
            }
        }

    }

    private fun setStartBoundService(){
        val mBoundServiceIntent = Intent(this@MainActivity,MyBoundService::class.java)
        bindService(mBoundServiceIntent,mServiceConnection, BIND_AUTO_CREATE)
    }

    private fun setStopBoundService(){
        unbindService(mServiceConnection)
    }

    private fun setActionJobIntentService(){
        /*
        MyJobIntentService dijalankan. Service tersebut akan melakukan pemrosesan obyek Intent yang dikirimkan dan menjalankan suatu proses yang berjalan di background.
         */
        val mStartIntentService = Intent(this@MainActivity,MyIntentService::class.java).apply {
            putExtra(MyIntentService.EXTRA_DURATION,5000L)
        }
        MyIntentService.enqueueWork(this@MainActivity,mStartIntentService)
    }

    private fun setActionBtnService(){
        val mStartServiceIntent = Intent(this,MyService::class.java)
        // Kode dibawah ini digunakan untuk menjalankan service
        startService(mStartServiceIntent)
    }


    private fun setToolBar(){
        supportActionBar?.apply {
            title = resources.getString(R.string.name_developer)
            subtitle = resources.getString(R.string.app_name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound){
            unbindService(mServiceConnection)
        }
    }
}