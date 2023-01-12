package com.candra.latihanmenyimpandatastore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

// Kode dibawah ini digunakan untuk membuat extension pada context dengan nama data Store dengan menggunakan
// Property delegation by preferencesDataStore.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToolbar()

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)


        /*
        Ketika MainActivity membutuhkan ViewModel, kita akan memanggil kelas ViewModelFactory untuk membuat ViewModel seperti berikut:
         */
        val mainViewModel = ViewModelProvider(this,ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActivate: Boolean ->
            if (isDarkModeActivate){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { compoundButton, isChecked ->
            mainViewModel.saveThemeSetting(isChecked)
        }

    }

    private fun setToolbar(){
        supportActionBar?.title = getString(R.string.name_developer)
        supportActionBar?.subtitle = getString(R.string.app_name)
    }


}