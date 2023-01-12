package com.candra.latihandrawernavigation

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.candra.latihandrawernavigation.databinding.ActivityMainBinding
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var profileImageUrl = "https://lh3.googleusercontent.com/-4qy2DfcXBoE/AAAAAAAAAAI/AAAAAAAABi4/rY-jrtntAi4/s640-il/photo.jpg"
    private lateinit var imageView:CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        /*
        NavigationView menampung semua menu dan sebuah header. Karena itulah jika Anda ingin mengubah komponen view yang terdapat di dalam header sebuah navigation view, maka proses casting/inisialisasi komponen harus dilakukan dengan cara seperti ini:
         NavigationView menampung semua menu dan sebuah header. Karena itulah jika Anda ingin mengubah komponen view yang terdapat di dalam header sebuah navigation view, maka proses casting/inisialisasi komponen harus dilakukan dengan cara seperti ini:
         */
        imageView = navView.getHeaderView(0).findViewById(R.id.imageView)
        val text = navView.getHeaderView(0).findViewById<TextView>(R.id.anda)
        text.text = "Julius Sinaga"
        Glide.with(this)
            .load(profileImageUrl)
            .into(imageView)
        //------------------------------------------------------------------------------------------//

        /*
        Selain id, ada name yang berisi nama kelas Fragment, label, dan layout. Dengan menambahkan kode ini, maka secara otomatis proses perpindahan Fragment sudah diatur oleh Navigation Component, sehingga Anda tidak perlu melakukan transaksi / pemanggilan fragment satu per satu. Keren bukan?

        Untuk melakukan proses otomatis tersebut, Anda perlu menambahkan code dibawah ini


        Berikut adalah fungsi masing-masing dari kode di atas:
        1. AppBarConfiguration berisi kumpulan id yang ada di dalam menu NavigationDrawer (baris 3). Jika id yang ada di dalam menu NavigationDrawer ditambahkan di AppBarConfiguration, maka AppBar akan berbentuk Menu NavigationDrawer. Jika tidak ditambahkan, maka akan menampilkan tanda panah kembali.
        2. setupActionBarWithNavController digunakan untuk mengatur judul AppBar agar sesuai dengan Fragment yang ditampilkan.
        3. Dan yang terakhir, setupWithNavController digunakan supaya NavigationDrawer menampilkan Fragment yang sesuai ketika menu dipilih.
        */

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_subway,R.id.nav_map
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //----------------------------------------------------------------------------------------
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*
    Sedangkan kode di atas digunakan untuk mengatur ketika tombol back ditekan. Misalnya ketika Anda di halaman CartFragment, jika Anda tekan tombol back, maka aplikasi tidak langsung keluar, melainkan akan menuju ke startDestination yang ada di Navigation Graph, yaitu HomeFragment.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    //----------------------------------------------------------------------------------------------
}

/*
Kesimpulan item
Ketika menggunakan group, atribut android:checkableBehavior= "single"  akan secara otomatis memberi penanda aktif ke item menu yang diklik dengan perubahan warna
 */