package com.candra.latihanmembuathalamansetting

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class MyPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var NAME: String
    private lateinit var EMAIL: String
    private lateinit var AGE: String
    private lateinit var PHONE: String
    private lateinit var LOVE: String

    private lateinit var namePreference: EditTextPreference
    private lateinit var emailPreference: EditTextPreference
    private lateinit var agePreference: EditTextPreference
    private lateinit var phonePreference: EditTextPreference
    private lateinit var isLoveMuPreference: CheckBoxPreference

    companion object {
        private const val DEFAULT_VALUE = "Tidak Ada"
    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
    }

    private fun init() {

        // Ini digunakan untuk memanggil semua komponen yang ada di xml preferences

        // menginisiasi kembali variabel yang kosong menjadi sebuah key yang telah ada
        NAME = resources.getString(R.string.key_name)
        EMAIL = resources.getString(R.string.key_email)
        AGE = resources.getString(R.string.key_age)
        PHONE = resources.getString(R.string.key_phone)
        LOVE = resources.getString(R.string.key_love)
        //----------------------------------------------------------------------

        // Kdoe dibawah ini digunakan untuk mencari widget preferences sesuai dengan key yang digunakan
        namePreference = findPreference<EditTextPreference> (NAME) as EditTextPreference
        emailPreference = findPreference<EditTextPreference>(EMAIL) as EditTextPreference
        agePreference = findPreference<EditTextPreference>(AGE) as EditTextPreference
        phonePreference = findPreference<EditTextPreference>(PHONE) as EditTextPreference
        isLoveMuPreference = findPreference<CheckBoxPreference>(LOVE) as CheckBoxPreference
        //-----------------------------------------------------------------------------------

    }

    // Kode dibawah ini digunakan untuk terjadi perubahan pada bagian onResume dan onPause
    override fun onResume() {
        super.onResume()
        // Menregister ketika aplikasi dibuka
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        // Menregister ketika aplikasi ditutup
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    /*
    Kode dibawah ini digunakan
    -untuk mengecek apakah terjadi perubahan pada data yang tersimpan. Jika terdapat value yang berubah maka akan memanggil listener onSharedPreferenceChanged. Untuk mendapatkan value tersebut, lakukan validasi terlebih dahulu. Dengan mencocokkan key mana yang berubah, hasilnya juga akan berubah.
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == NAME) {
            namePreference.summary = sharedPreferences.getString(NAME, DEFAULT_VALUE)
        }

        if (key == EMAIL) {
            emailPreference.summary = sharedPreferences.getString(EMAIL, DEFAULT_VALUE)
        }

        if (key == AGE) {
            agePreference.summary = sharedPreferences.getString(AGE, DEFAULT_VALUE)
        }

        if (key == PHONE) {
            phonePreference.summary = sharedPreferences.getString(PHONE, DEFAULT_VALUE)
        }

        if (key == LOVE) {
            isLoveMuPreference.isChecked = sharedPreferences.getBoolean(LOVE, false)
        }
    }

    private fun setSummaries() {
        // Ini digunakan untuk mengubah value dari EditTextPreference menjadi nilai yang baru sesuai dengan data yang disimopan
        val sh = preferenceManager.sharedPreferences
        namePreference.summary = sh.getString(NAME, DEFAULT_VALUE)
        emailPreference.summary = sh.getString(EMAIL, DEFAULT_VALUE)
        agePreference.summary = sh.getString(AGE, DEFAULT_VALUE)
        phonePreference.summary = sh.getString(PHONE, DEFAULT_VALUE)
        isLoveMuPreference.isChecked = sh.getBoolean(LOVE, false)
        //------------------------------------------------------------------------------
    }
}

/*
kesimpulan
Di sini kita menggunakan PreferenceScreen sebagai layoutnya, karena kita ingin membuat Setting Preference. Attribute app:iconSpaceReserved digunakan untuk menghapus space di component.


 Komponen PreferenceScreen sendiri tidak terlalu banyak, di sini hanya menggunakan 2 yaitu EditTextPreference dan CheckBoxPreference. Hampir sama dengan pembuatan widget atau View di sebuah layout. Hanya saja, jika di layout Anda menggunakan id sebagai nama kunci dari sebuah obyek dan di PreferenceScreen Anda hanya menggunakan key sebagai kata kuncinya
 */