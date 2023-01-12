package com.candra.latihannetworkingwithloopj

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.latihannetworkingwithloopj.Help.Companion.failedConnection
import com.candra.latihannetworkingwithloopj.Help.Companion.showProgressBar
import com.candra.latihannetworkingwithloopj.databinding.ActivityQutesListBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class ListQutesActivity: AppCompatActivity() {

    val list = ArrayList<String>()


    companion object {
        private val TAG = ListQutesActivity::class.java.simpleName
        private val urlList = "https://quote-api.dicoding.dev/list"
    }

    private lateinit var binding: ActivityQutesListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQutesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        setLayoutManager()

        getAllListQuotes()

    }

    private fun setToolbar() {
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = resources.getString(R.string.listOfQuetes)
    }

    private fun setLayoutManager() {
        val layoutManager1 = LinearLayoutManager(this@ListQutesActivity)
        binding.recyclerView.apply {
            layoutManager = layoutManager1
            addItemDecoration(DividerItemDecoration(this@ListQutesActivity,layoutManager1.orientation))
        }

    }

    private fun getAllListQuotes() {
        val client = AsyncHttpClient()

        client.get(urlList,object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                // If Connection Success
                // To Do In Here
                successConnection(responseBody)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                // If Connection Fail
                // To Do In Here
                failedConnection(statusCode,error = error,context = this@ListQutesActivity
                ,progressBar = binding.progressBar)
            }
        })
    }


    private fun successConnection(responseBody: ByteArray) {

        showProgressBar(false,binding.progressBar)
        val result = String(responseBody)


        try {
            val jsonArray = JSONArray(result)
            jsonArray.let {
                for (i in 0 until it.length()){
                    it.getJSONObject(i).apply {
                        val quote = getString("en")
                        val author = getString("author")
                        list.add("\n$quote\n- $author\n")
                    }
                }
                val adapter = QuoteAdapter(list)
                binding.recyclerView.adapter = adapter
            }


        }catch (e: Exception){
            Toast.makeText(this@ListQutesActivity,e.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}