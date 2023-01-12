package com.candra.latihannavigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.candra.latihannavigation.databinding.FragmentCategoryBinding

class CategoryFragment: Fragment()
{
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater,container,false)

        setActionClick()

        return binding.root
    }

    private fun setActionClick(){
        binding.btnCategoryLifestyle.setOnClickListener {
            sendDataToAnotherFragment(it)
        }
    }

    private fun sendDataToAnotherFragment(view: View){
        // Kode ini digunakan untuk mengirim data dari Category Fragment ke DetailCategory Fragment
        val toDetailCategoryFragment = CategoryFragmentDirections.actionCategoryFragmentToDetailCategoryFragment()
        //-----------------------------------------------------------------------------------------

        // Data yang ingin dikirimkan
        toDetailCategoryFragment.name = "Candra Julius Sinaga"
        toDetailCategoryFragment.stock = 10
        //--------------------------------


        view.findNavController().navigate(toDetailCategoryFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}