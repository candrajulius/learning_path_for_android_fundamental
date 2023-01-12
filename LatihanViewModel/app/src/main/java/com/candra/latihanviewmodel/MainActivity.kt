package com.candra.latihanviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.candra.latihanviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // How to way fast for iniziation viewModel
    private val viewModel: MainViewModel by viewModels()

//    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // How to way slow iniziation viewModel
//        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setDataWithViewModel()


        displayResult()

        setToolbar()
    }

    private fun setDataWithViewModel() {

        with(binding){
            btnCalculate.setOnClickListener {
                val width = edtWidth.text.toString()
                val height = edtHeight.text.toString()
                val lenght = edtLength.text.toString()

                val emptyForm = resources.getString(R.string.kosong)

                when{
                    width.isEmpty() -> edtWidth.error = emptyForm
                    height.isEmpty() -> edtHeight.error = emptyForm
                    lenght.isEmpty() -> edtLength.error = emptyForm
                    else -> {
                        viewModel.calculate(width = width,height = height,length = lenght)
                        displayResult()
                    }
                }
            }
        }
    }

    private fun displayResult(){
        with(binding){
            tvResult.text = viewModel.result.toString()
        }
    }




    private fun setToolbar(){
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }
}