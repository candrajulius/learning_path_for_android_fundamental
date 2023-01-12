package com.candra.latihanjunit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.candra.latihanjunit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mainViewModel = MainViewModel(cuboidModel = CuboidModel())

        with(binding){
            btnCalculateCircumference.setOnClickListener(this@MainActivity)
            btnCalculateSurfaceArea.setOnClickListener(this@MainActivity)
            btnCalculateVolume.setOnClickListener(this@MainActivity)
            btnSave.setOnClickListener(this@MainActivity)
        }

    }

    override fun onClick(p0: View?) {

        with(binding){

            val emptyField = resources.getString(R.string.emptyField)

            val length = edtLength.text.toString().trim()
            val width =  edtWidth.text.toString().trim()
            val height = edtHeight.text.toString().trim()

            when{
                TextUtils.isEmpty(length) -> {
                    edtLength.error = emptyField
                }
                TextUtils.isEmpty(width) -> {
                    edtWidth.error = emptyField
                }
                TextUtils.isEmpty(height) -> {
                    edtHeight.error = emptyField
                }
                else -> {
                    val valueLength = length.toDouble()
                    val valueWidth = width.toDouble()
                    val valueHeight = height.toDouble()

                    when(p0?.id){
                        R.id.btn_save -> {
                            mainViewModel.save(valueLength,valueWidth,valueHeight)
                            visible()
                        }
                        R.id.btn_calculate_circumference -> {
                            tvResult.text = mainViewModel.getCircumference().toString()
                            gone()
                        }
                        R.id.btn_calculate_surface_area -> {
                            tvResult.text = mainViewModel.getSurfaceArea().toString()
                            gone()
                        }
                        R.id.btn_calculate_volume -> {
                            tvResult.text = mainViewModel.getVolume().toString()
                            gone()
                        }
                    }
                }
            }
        }
    }


    private fun visible(){
        with(binding){
            btnCalculateVolume.visibility = View.VISIBLE
            btnCalculateCircumference.visibility = View.VISIBLE
            btnCalculateSurfaceArea.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
        }
    }

    private fun gone(){
        with(binding){
            btnCalculateVolume.visibility = View.GONE
            btnCalculateCircumference.visibility = View.GONE
            btnCalculateSurfaceArea.visibility = View.GONE
            btnSave.visibility = View.VISIBLE
        }
    }

}