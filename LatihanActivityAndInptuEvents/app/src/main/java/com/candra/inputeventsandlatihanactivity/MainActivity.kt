package com.candra.inputeventsandlatihanactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.candra.inputeventsandlatihanactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    companion object{
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Digunakan untuk menyimpan data yang ada di TextView dengan menggunakan
        // Bundle
        if (savedInstanceState != null){
            /*
            Mengambil value dengan menggunakan methode savedInstanceState dengan key dan value
             */
            val result = savedInstanceState.getString(STATE_RESULT)

            // menset data yang didapat tadi kedalam textView
            binding.tvResult.text = result
        }

        binding.btnCalculate.setOnClickListener(this)
    }

    // Menggunakan function ini untuk menyimpan data dalam bentuk bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT,binding.tvResult.text.toString())
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_calculate -> calculateAllVolume()
        }
    }

    private fun calculateAllVolume(){

        val warning = resources.getString(R.string.fieldNotEmpty)

        val inputLength = binding.edtLength.text.toString().trim()
        val inputWidth = binding.edtWidth.text.toString().trim()
        val inputHeight = binding.edtHeight.text.toString().trim()

        if (inputLength.isEmpty()){
            binding.edtLength.error = warning
        }else if (inputWidth.isEmpty()){
            binding.edtWidth.error = warning
        }else if (inputHeight.isEmpty()){
            binding.edtHeight.error = warning
        }else{
            val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
            binding.tvResult.text = volume.toString()
        }
    }

}