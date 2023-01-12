package com.candra.latihanroomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.latihanroomdatabase.databinding.ActivityMainBinding
import com.candra.latihanroomdatabase.viewmodel.MainViewModel
import com.candra.latihanroomdatabase.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainAdapter by lazy { NoteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataAll()

        val maiNViewModel = obtainViewModel(this)

        maiNViewModel.getAllNotes().observe(this){
            it?.let { mainAdapter.setListNotes(it) }
        }

        setActionFabAdd()

        setToolbar()

    }

    private fun setToolbar(){
        supportActionBar?.apply {
            title = "Candra Julius Sinaga"
            subtitle = resources.getString(R.string.app_name)
        }
    }

    private fun obtainViewModel(mainActivity: MainActivity): MainViewModel{
        val factory = ViewModelFactory.getInstance(mainActivity.application)
        return ViewModelProvider(mainActivity,factory).get(MainViewModel::class.java)
    }

    private fun setActionFabAdd(){
        binding.fabAdd.setOnClickListener{
            startActivity(Intent(this@MainActivity,ActitivityNoteAddUpdate::class.java))
        }
    }

    private fun setDataAll(){
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }
}