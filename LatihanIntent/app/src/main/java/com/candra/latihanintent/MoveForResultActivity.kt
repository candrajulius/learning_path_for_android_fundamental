package com.candra.latihanintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.candra.latihanintent.databinding.ActivityMoveForResultBinding

class MoveForResultActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val EXTRA_SELECTED_VALUE = "extra_selected_value"
        const val RESULT_CODE = 110
    }

    private lateinit var binding: ActivityMoveForResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveForResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnChoose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_choose){
            if(binding.rgNumber.checkedRadioButtonId > 0)
            {
               var value = 0
               when(binding.rgNumber.checkedRadioButtonId)
               {
                R.id.rb_50 -> value = 50
                R.id.rb_100 -> value = 100
                R.id.rb_150 -> value = 150
                R.id.rb_200 -> value = 200
               }
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_SELECTED_VALUE,value)
                setResult(RESULT_CODE,resultIntent)
                finish()
            }
        }
    }
}