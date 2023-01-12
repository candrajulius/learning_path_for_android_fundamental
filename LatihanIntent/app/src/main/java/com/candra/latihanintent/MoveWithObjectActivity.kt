package com.candra.latihanintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.candra.latihanintent.databinding.ActivityMoveWithObjectBinding

class MoveWithObjectActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_PERSON = "extra_person"
    }

    private lateinit var binding: ActivityMoveWithObjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveWithObjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receivedDataFromParcelable()
    }

    private fun receivedDataFromParcelable(){
        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON)
        if (person != null){
            val text = "Name : ${person.name.toString()},\n Email: ${person.email},\n Age : ${person.age}" +
                    "\n Location : ${person.city}"
            binding.tvObjectReceived.text = text
        }
    }
}