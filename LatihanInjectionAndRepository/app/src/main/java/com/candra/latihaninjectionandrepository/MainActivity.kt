package com.candra.latihaninjectionandrepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.candra.latihaninjectionandrepository.adapter.SectionPagerAdapter
import com.candra.latihaninjectionandrepository.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setPagerAdapteInHere()
    }

    private fun setPagerAdapteInHere(){
        val sectionPagerAdapter = SectionPagerAdapter(this)
        mainBinding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(
            mainBinding.tabs,mainBinding.viewPager
        ){ tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        setToolbar()
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home,R.string.bookmark)
    }

    private fun setToolbar(){
        supportActionBar?.title = "Candra Julius Sinaga"
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
        supportActionBar?.elevation = 0f
    }

}