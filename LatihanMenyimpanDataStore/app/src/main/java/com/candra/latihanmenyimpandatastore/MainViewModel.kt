package com.candra.latihanmenyimpandatastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val pref: SettingPreferences
): ViewModel()
{

    /*
    Perlu diperhatikan juga bahwa nilai kembalian dari fungsi ini berupa Flow. Flow merupakan salah satu bagian dari Coroutine yang digunakan untuk mengambil data secara berkelanjutan (data stream) dengan jumlah yang banyak. Flow sering digunakan untuk membuat reactive programming yang akan dipelajari lebih lanjut pada kelas Expert. Untuk saat ini, karena keluaran dari DataStore masih berupa Flow, maka kita perlu mengubahnya menjadi LiveData pada VIewModel dengan cara seperti berikut
     */
    // Ini digunakan dalam membaca data
    fun getThemeSettings(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    /*
    Karena itulah pada MainViewModel kita menggunakan viewModelScopeuntuk menjalankan suspend function seperti berikut
     */
    // Ini digunakan untuk menyimpan data
    fun saveThemeSetting(isDarkModeActivate: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            pref.saveThemeSetting(isDarkModeActivate)
        }
    }

}