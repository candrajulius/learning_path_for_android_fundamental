package com.candra.latihantaskandbackstack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.candra.latihantaskandbackstack.databinding.ActivityDetailBinding

class DetailActivity: AppCompatActivity()
{
    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_MESSAGE = "extra_message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)

        with(binding){
            tvTitle.text = title
            tvMessage.text = message
        }
    }
}