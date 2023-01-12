package com.candra.latihannavigation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.candra.latihannavigation.R
import com.candra.latihannavigation.databinding.ActivityProfileBinding

class ProfileActivity: AppCompatActivity()
{

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
    }

    private fun setToolbar(){
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }
}