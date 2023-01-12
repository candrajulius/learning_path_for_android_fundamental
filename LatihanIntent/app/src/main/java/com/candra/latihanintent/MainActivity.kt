package com.candra.latihanintent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.candra.latihanintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null){
            val selectedValue = result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE,0)
            binding.mtvResult.text = "Hasil : $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMoveActivity.setOnClickListener(this)
        binding.btnMoveActivityData.setOnClickListener(this)
        binding.btnMoveActivityObject.setOnClickListener(this)
        binding.btnDialNumber.setOnClickListener(this)
        binding.btnMoveForResult.setOnClickListener(this)

        setToolbar()
    }

    private fun setToolbar(){
        supportActionBar?.title = resources.getString(R.string.developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }

    private fun moveActivityWithData(){
        val moveWithDataIntent = Intent(this@MainActivity,MoveActivityWithData::class.java)
        moveWithDataIntent.putExtra(MoveActivityWithData.EXTRA_NAME,"DicodingAcademy Boy")
        moveWithDataIntent.putExtra(MoveActivityWithData.EXTRA_AGE,5)
        startActivity(moveWithDataIntent)
    }

    private fun moveActvity(){
        val moveIntent = Intent(this@MainActivity,MoveActivity::class.java)
        startActivity(moveIntent)
    }

    private fun moveActivityObject(){
        val person = Person(
            "Candra Julius Sinaga",
            5,
            "candrajulius1@gmail.com",
            "Medan"
        )
        val moveWithObjectIntent = Intent(this@MainActivity,MoveWithObjectActivity::class.java)
        moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON,person)
        startActivity(moveWithObjectIntent)
    }

    private fun resultNumberActivity(){
        val moveResultIntent = Intent(this@MainActivity,MoveForResultActivity::class.java)
        resultLauncher.launch(moveResultIntent)
    }

    private fun dialNumber(){
        val phoneNumber = "082311558341"
        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialPhoneIntent)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_move_activity -> moveActvity()
            R.id.btn_move_activity_data -> moveActivityWithData()
            R.id.btn_move_activity_object -> moveActivityObject()
            R.id.btn_dial_number -> dialNumber()
            R.id.btn_move_for_result -> resultNumberActivity()
        }
    }

/*
Bedah Code
registerForActivityResult
Untuk membuat sebuah Activity yang dapat mengembalikan nilai, kita perlu membuat objek ActivityResultLauncher terlebih dahulu

Anda perlu mendaftarkan jenis kembalian ke sistem dengan menggunakan kode registerForActivityResult dengan parameter ActivityResultContract berupa ActivityResultContract. Hal ini karena kita akan mendapatkan nilai kembalian setelah memanggil Activity baru. Perlu diketahui bahwa kita juga bisa mendapatkan nilai kembalian dari selain Activity, contohnya seperti foto dari galeri dengan mendefinisikan contract yang berbeda.

Selanjutnya, perbedaan mendasar antara perpindahan Activity untuk menghasilkan nilai balik dengan tidak, adalah pada metode untuk menjalankan object Intent-nya. Sebelumnya kita menggunakan startActivity(intent) untuk berpindah Activity. Nah, kali ini kita menggunakan launch(intent) dari object ActivityResultLauncher.

Mengatur dan Membaca Nilai Hasil
Pada MoveForResultActivity kita memilih satu angka yang kita suka, sebagai contoh angka 150. Kemudian tekanlah tombol ‘Pilih’. Maka baris kode di bawah ini akan dijalankan.

Pada kode di atas berfungsi untuk melakukan validasi nilai dari obyek RadioButton yang dipilih. Bila ada nilai dari radiobutton, maka proses selanjutnya adalah menentukan obyek RadioButton mana yang diklik berdasarkan nilai dari rgNumber.getCheckedRadioButtonId().

Mengapa kita tidak memeriksa langsung ke objek RadioButton? Karena kita menggunakan RadioGroup sebagai parent pada objek-objek RadioButton. Secara otomatis kita bisa mendapatkan mana obyek RadioButton yang dipilih dengan rgNumber.getCheckedRadioButtonId()

Kita membuat sebuah intent tanpa ada inputan apapun di konstruktornya. Kemudian kita meletakkan variabel value ke dalam metode putExtra(Key, Value) dengan EXTRA_SELECTED_VALUE bertipekan static string dan bernilai “extra_selected_value”. Kemudian kita jadikan obyek resultIntent yang telah dibuat sebelumnya menjadi parameter dari setResult(RESULT_CODE, Intent).

Ketika MoveForResultActivity telah tertutup sempurna, maka callback ActivityResultLauncher pada MainActivity akan dijalankan.

Di sinilah MainActivity akan merespon terhadap nilai balik yang dikirimkan oleh MoveForResultActivity. Pada baris 4 di atas, dilakukan perbandingan apakah nilai resultCode sama yang dikirim oleh MoveForResultActivity. Selain itu, juga diperiksa, apakah data yang dikembalikan bernilai null atau tidak. Bila semua kondisi terpenuhi, data RadioButton yang dipilih akan ditampilkan di TextView tvResult.
*/


}