package com.candra.latihaninjectionandrepository.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.candra.latihaninjectionandrepository.fragment.Newsfragment
import com.candra.latihaninjectionandrepository.fragment.Newsfragment.Companion.ARG_TAB
import com.candra.latihaninjectionandrepository.fragment.Newsfragment.Companion.TAB_BOOKMARK
import com.candra.latihaninjectionandrepository.fragment.Newsfragment.Companion.TAB_NEWS

class SectionPagerAdapter internal constructor(
    activity: AppCompatActivity
): FragmentStateAdapter(activity)
{
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = Newsfragment()
        val bundle = Bundle()
        if (position == 0){
            bundle.putString(ARG_TAB,TAB_NEWS)
        }else{
            bundle.putString(ARG_TAB, TAB_BOOKMARK)
        }
        fragment.arguments = bundle
        return fragment
    }
}