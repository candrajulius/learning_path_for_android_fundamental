package com.candra.latihanintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.candra.latihanintent.databinding.ActivityMoveWithDataBinding

class MoveActivityWithData : AppCompatActivity() {

    companion object{
        const val EXTRA_AGE = "extra_age"
        const val EXTRA_NAME = "extra_name"
    }

    private lateinit var binding: ActivityMoveWithDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveWithDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveData()
    }

    private fun receiveData(){
        val name = intent.getStringExtra(EXTRA_NAME)
        val age = intent.getIntExtra(EXTRA_AGE,0)

        val text = "Name: $name, Your Age: $age"
        binding.tvDataReceived.text = text
    }
}