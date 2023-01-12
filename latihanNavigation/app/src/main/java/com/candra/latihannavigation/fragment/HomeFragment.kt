package com.candra.latihannavigation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.candra.latihannavigation.R
import com.candra.latihannavigation.databinding.HomeFragmentBinding

class HomeFragment: Fragment()
{

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCategory.setOnClickListener {
//            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_categoryFragment)
            view.findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }

        binding.btnProfile.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_profileActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /*
    kesimpulan
    popUpToInclusive
    1. Jika bernilai true maka fragment sebelumnya akan dibersihkan
    2. Jika bernilai false maka fragment sebelumnya tidak akan dibersihkan

Animasi

  - enterAnim, yaitu animasi ketika masuk.

- exitAnim, yaitu animasi ketika keluar (berpindah ke fragment lain).

- popEnterAnim, yaitu animasi ketika masuk dari back stack.

- popExitAnim, yaitu animasi ketika keluar dari back stack.
     */


}