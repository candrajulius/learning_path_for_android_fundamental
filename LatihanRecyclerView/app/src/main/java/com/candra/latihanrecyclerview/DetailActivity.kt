package com.candra.latihanrecyclerview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.candra.latihanrecyclerview.model.Hero

class DetailActivity: AppCompatActivity()
{
    companion object{
        private const val TAG = "DetailActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        receivedData()
        showToolbar()
    }

    private fun receivedData(){
        val received = intent.getParcelableExtra<Hero>("DATA")
        Log.d(TAG, "receivedData: ${received?.name.toString()}")
    }

    private fun showToolbar(){
        supportActionBar?.title = resources.getString(R.string.developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }
}