package com.candra.restaurantreview

import android.view.View
import android.widget.ProgressBar

class Help {

    companion object{
        fun showProgressBar(isLoading: Boolean,progressBar: ProgressBar)
        {
            if (isLoading) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE
        }
    }

}