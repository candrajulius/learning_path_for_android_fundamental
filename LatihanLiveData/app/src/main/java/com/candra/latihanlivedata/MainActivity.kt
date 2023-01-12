package com.candra.latihanlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.candra.latihanlivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setToolbar()

        subscribe()
    }

    private fun subscribe() {
        val elapsedTimerObserver = Observer<Long> { aLong ->
            val newText = this@MainActivity.resources.getString(R.string.seconds,aLong)
            binding.timerTextview.text = newText
        }
        mViewModel.getElapsedTime().observe(this,elapsedTimerObserver)

    }

    private fun setToolbar(){
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }

}