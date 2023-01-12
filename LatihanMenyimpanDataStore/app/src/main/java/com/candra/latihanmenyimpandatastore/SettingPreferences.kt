package com.candra.latihanmenyimpandatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(
    private val dataStore: DataStore<Preferences>
){

    /*
    Untuk menyimpan data, yang Anda perlukan hanyalah instance DataStore dan Key pada SettingPreferences.

     Digunakan untuk memberikan key yang harus diambil dari data prefence

     Perlu diketahui, Key ini harus unik supaya tidak tercampur dengan data lain. Key ini juga akan diperlukan untuk mengambil data yang sama. Selain itu, key juga harus sesuai dengan tipe data yang akan disimpan. Sebagai contoh, jika ingin menyimpan pengaturan tema yang berupa True/False, gunakanlah Boolean. Apabila Anda ingin menyimpan data lain dengan tipe data yang berbeda, Anda harus menyesuaikan bagian ini juga.
     */
    private val THEME_KEY = booleanPreferencesKey("theme_setting")



    /*
    Sama seperti proses menyimpan, yang Anda perlukan untuk membaca data hanyalah instance DataStore dan Key pada SettingPreferences.

    Untuk mengambil data yang sudah disimpan, kita menggunakan fungsi map pada variabel data. Pastikan Anda menggunakan key yang sama dengan saat Anda menyimpannya untuk mendapatkan data yang tepat. Selain itu, Anda juga dapat menambahkan elvis operator untuk memberikan nilai default jika datanya masih kosong/null.
     */
    fun getThemeSetting(): Flow<Boolean>{
        return dataStore.data.map {  preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    /*
    Untuk menyimpan data, kita menggunakan fungsi lambda edit dengan parameter preferences yang merupakan MutablePrefereces. Untuk mengubah data, Anda perlu menentukan key data yang ingin diubah dan isi datanya. Selain itu, karena edit adalah suspend function, maka ia harus dijalankan di coroutine atau suspend function juga.
     */
    suspend fun saveThemeSetting(isDarkModeActivate: Boolean){
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActivate
        }
    }

    /*
    Karena itu untuk membuat SettingPreference, kita tidak menggunakan constructor secara langsung, melainkan melalui fungsi getInstance yang berfungsi sebagai Singleton seperti berikut:
     */

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
/*
Khusus di sistem Android, terdapat multi-threading yang bisa menjalankan kode di thread yang berbeda-beda, sehingga bisa saja instance dibuat di thread yang berbeda. Untuk itulah dibutuhkan kode synchronized untuk membuat semua thread tersinkronisasi. Dengan cara ini, hanya satu thread yang boleh menjalankan fungsi yang sama di waktu bersamaan.
 */