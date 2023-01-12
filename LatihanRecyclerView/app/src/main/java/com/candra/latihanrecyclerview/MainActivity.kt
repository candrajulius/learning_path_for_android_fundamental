package com.candra.latihanrecyclerview

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.candra.latihanrecyclerview.adapter.ListHeroAdapter
import com.candra.latihanrecyclerview.databinding.ActivityMainBinding
import com.candra.latihanrecyclerview.model.Hero

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Hero>()
    private val listHeroAdapter by lazy { ListHeroAdapter(list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list.addAll(listHeroes)

        /*
        Digunakan untuk melakukan optimasi ukuran lebar dan tinggi secara otomatis. Nilai
        lebar dan tinggi RecyclerView menjadi konstan. Terlebih jika kita memiliki koleksi data
        yang dinamis untuk proses penambahan,perpindahan, dan pengurangan item dari koleksi data
         */
        binding.rvHeroes.setHasFixedSize(true)
        showRecyclerList()

    }

    private val listHeroes: ArrayList<Hero>
    get() {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.getStringArray(R.array.data_photo)

        val listHero = ArrayList<Hero>()

        for (i in dataName.indices){
            val hero = Hero(dataName[i],dataDescription[i],dataPhoto[i])
            listHero.add(hero)
        }

        return listHero
    }

    private fun showSelectedHero(hero: Hero){
        Toast.makeText(this,"Kamu memilih ${hero.name}",Toast.LENGTH_SHORT).show()
        val intentDetail = Intent(this@MainActivity,DetailActivity::class.java)
        intentDetail.putExtra("DATA",hero)
        startActivity(intentDetail)
    }

    private fun showRecyclerList(){

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvHeroes.layoutManager = GridLayoutManager(this,2)
        }else{
            binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        }
        binding.rvHeroes.adapter = listHeroAdapter

        listHeroAdapter.setOnItemClickCallback(object: ListHeroAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Hero) {
                showSelectedHero(data)
            }

        })
    }
}