package com.candra.latihanoptionmenu

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.candra.latihanoptionmenu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
    }

    private fun setToolbar(){
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)

        actionToSearchView(menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu1 -> {
                createFragment()
                true
            }
            R.id.menu2 -> {
                startActivity(Intent(this@MainActivity,MenuActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun createFragment(){
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.fragent_container,MenuFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun actionToSearchView(menu: Menu?){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null){
                    Toast.makeText(this@MainActivity,"Keyword be empty",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity,query,Toast.LENGTH_SHORT).show()
                    // Fungsi dari clearFocus dibawah ini yang dipanggil supaya tidak ada
                    // duplikasi dalam pemanggilan fungsi onQueryTextSubmit
                    searchView.clearFocus()
                    //------------------------------------------------------------------
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    /*
    Kesimpulan
    Terdapat 5 kondisi yang dapat dipasang pada tag app:showAsAction:

    ifRoom, akan menampilkan action ketika ada ruangan pada action bar.
    withText, akan menampilkan actionitem beserta judulnya.
    never, tidak akan pernah ditampilkan pada action bar dan hanya akan ditampilkan pada overflow menu.
    always, akan selalu tampil pada action bar.
    collapseActionView, berhubungan dengan komponen collapsible .
     */

}