package com.candra.latihannetworkingwithloopj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.candra.latihannetworkingwithloopj.Help.Companion.failedConnection
import com.candra.latihannetworkingwithloopj.Help.Companion.showProgressBar
import com.candra.latihannetworkingwithloopj.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object{
        private val TAG = MainActivity::class.java.simpleName
        private val url = "https://quote-api.dicoding.dev/random"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        getRandomQuote()

        binding.btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity,ListQutesActivity::class.java))
        }

    }

    private fun setToolbar(){
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }

    private fun getRandomQuote(){
        showProgressBar(show = true,binding.progressBar)
        val client = AsyncHttpClient()
        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                // If connection success
                showProgressBar(false,binding.progressBar)
                val result = String(responseBody)
                Log.d(TAG, "onSuccess: $result")

                try{
                    val responObject = JSONObject(result)

                    responObject.apply {
                       binding.tvQuates.text = getString("en")
                       binding.tvAuthor.text = getString("author")
                    }

                }catch (e: Exception){
                    Toast.makeText(this@MainActivity,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                // If connection failed
                failedConnection(statusCode,error,this@MainActivity,binding.progressBar)
            }

        })
    }



}