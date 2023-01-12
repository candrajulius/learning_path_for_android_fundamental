package com.candra.latihannetworkingwithloopj

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

class Help {
    companion object{

        fun showProgressBar(show: Boolean, progressBar: ProgressBar){
            if (show) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }

        fun failedConnection(statusCode: Int,error: Throwable?,context: Context,progressBar: ProgressBar){
            showProgressBar(false,progressBar)
            val errorMessage = when(statusCode){
                401 -> "$statusCode : Bad Request"
                403 -> "$statusCode : Forbidden"
                404 -> "$statusCode : Not Found"
                else -> "$statusCode : ${error?.message}"
            }
            Toast.makeText(context,errorMessage, Toast.LENGTH_SHORT).show()
        }

    }
}