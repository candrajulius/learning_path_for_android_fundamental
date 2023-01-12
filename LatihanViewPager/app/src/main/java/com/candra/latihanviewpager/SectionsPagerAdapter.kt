package com.candra.latihanviewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter
    (activity: AppCompatActivity
): FragmentStateAdapter(activity)
{
    var appname: String = ""

    // Ini digunakan untuk menampilkan berapa fragment
    // Tergantung fragment yang anda buat.. jangan melebihi fragment yang anda buat
    override fun getItemCount(): Int {
        return 3
    }

    // Ini kode yang digunakan untuk membuat fragment
    override fun createFragment(position: Int): Fragment {

      val fragment = HomeFragment()
      sendData(fragment,position)
      return fragment
    }

    private fun sendData(fragment: Fragment,position: Int){
        fragment.arguments = Bundle().apply {
            putInt(HomeFragment.ARG_SECTION_NUMBER,position + 1)
            putString(HomeFragment.ARG_NAME,appname)
        }
    }
}