package com.candra.latihannavigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.candra.latihannavigation.R
import com.candra.latihannavigation.databinding.DetailCategoryFragmentBinding

/*
Programmer : Candra Julius Sinaga
Youtube    :
Website    : https://code.cjsflow.com
 */

class DetailCategoryFragment: Fragment()
{

    private var _binding: DetailCategoryFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailCategoryFragmentBinding.inflate(inflater,container,false)

        receivedDataFromFragment()

        setBackToFragmentHome()

        return binding.root
    }

    private fun setBackToFragmentHome(){
        binding.btnProfile.setOnClickListener { view ->
            // Code ini diguanakan untuk berpindah antar fragment atau activity
            view.findNavController().navigate(R.id.action_detailCategoryFragment_to_homeFragment)
        }
    }

    private fun receivedDataFromFragment(){
        // Kedua kode ini digunakan untuk menerima data dalam bentuk safeargs dari fragmentCategory
        val dataName = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).name
        val dataStock = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).stock
        //_________________________________________________________________________________________
        binding.tvCategoryName.text = dataName
        binding.tvCategoryDescription.text = "Stock : $dataStock"
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}