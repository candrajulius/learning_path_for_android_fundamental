package com.candra.restaurantreview.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.candra.restaurantreview.Constant.Companion.RESTAURANT_ID
import com.candra.restaurantreview.Help.Companion.showProgressBar
import com.candra.restaurantreview.R
import com.candra.restaurantreview.adapter.ReviewAdapter
import com.candra.restaurantreview.databinding.ActivityMainBinding
import com.candra.restaurantreview.model.CustomerReview
import com.candra.restaurantreview.model.PostReviewResponse
import com.candra.restaurantreview.model.Restaurant
import com.candra.restaurantreview.model.RestaurantResponse
import com.candra.restaurantreview.service.ApiConfig
import com.candra.restaurantreview.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mViewModel: MainViewModel

    private lateinit var adapter: ReviewAdapter

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLayoutManager()


        with(binding){
            btnSend.setOnClickListener { view ->
                mViewModel.postReview(edReview.text.toString())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken,0)
            }
        }

        // Inisiasi viewModel
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Membuat data yang mutable listReview menjadi di oberver
        /*
        Perlu Anda ketahui bahwa customerReviews adalah custom variabel untuk hasil LiveData yang ada di ViewModel. Jika Anda tidak menuliskan customerReviews-> maka variabel default-nya adalah it
         */
        mViewModel.listReview.observe(this) { listReview ->
            listReview?.let { setReviewData(it) }
        }

        // Membuat data yang mutabel isLoading menjadi di Observer
        mViewModel.isLoading.observe(this){
            showProgressBar(it,binding.progressBar)
        }

        // Membuat data yang mutabel restaurant menjadi di Observer
        mViewModel.restaurant.observe(this){restaurant ->
            restaurant?.let {
                setRestaurantData(it)
            }
        }

        mViewModel.snackbarText.observe(this){ data ->
            data.getContentIfNotHandled()?.also { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        setToolbar()
    }

    private fun setReviewData(restaurant: List<CustomerReview>){

       val listReview = restaurant.map {
           "${it.review} \n- ${it.name}"
       }

        with(binding){
            adapter = ReviewAdapter(listReview)
            rvReview.adapter = adapter
            edReview.setText("")
        }
    }

    private fun setRestaurantData(restaurant: Restaurant){
        with(binding){
            tvTitle.text = restaurant.name
            tvDescription.text = restaurant.description
            tvRating.text = restaurant.rating.toString()
            Glide.with(this@MainActivity)
                .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                .into(ivPicture)
        }
    }

    private fun setLayoutManager(){
        val layoutManager1 = LinearLayoutManager(this@MainActivity)

        binding.rvReview.apply {
            layoutManager = layoutManager1
            addItemDecoration(DividerItemDecoration(this@MainActivity,layoutManager1.orientation))
        }
    }

    private fun setToolbar(){
        supportActionBar?.title = resources.getString(R.string.name_developer)
        supportActionBar?.subtitle = resources.getString(R.string.app_name)
    }


    /*
    LiveData => bersifat read only
    MutableLiveData => bisa diubah valuenya

    Perbedaan dari pstValue setValue dan getvalue

    - setValue()
      Menetapkan sebuah nilai dari LiveData. Jika ada observer yang aktif, nilai akan dikirim kepada mereka. Metode ini harus dipanggil dari main thread.

    - postValue()
      Posting tugas ke main thread untuk menetapkan nilai yang diberikan dari background thread. Jika Anda memanggil metode ini beberapa kali sebelum main thread menjalankan tugas yang di-posting, hanya nilai terakhir yang akan dikirim.

    - getValue()
      Mendapatkan nilai dari sebuah LiveData

      Intinya adalah setValue() bekerja di main thread dan postValue() bekerja di background thread.

      Inilah yang disebut dengan encapsulation pada LiveData, yaitu dengan membuat data yang bertipe MutableLiveData menjadi private (_listReview) dan yang bertipe LiveData menjadi public (listReview). Cara ini disebut dengan backing property. Dengan begitu Anda dapat mencegah variabel yang bertipe MutableLiveData diubah dari luar class. Karena memang seharusnya hanya ViewModel-lah yang dapat mengubah data.
     */

    /*
    Kesimpulan Single Event
    Seperti yang sudah Anda pahami di bahasa Kotlin, T adalah tipe generic yang bisa digunakan supaya kelas ini dapat membungkus berbagai macam data. Data yang dibungkus tersebut kemudian akan dimasukkan ke dalam variabel content.

    Nah, fungsi utama dari kelas ini yaitu terdapat pada fungsi getContentIfNotHandled(). Fungsi tersebut akan memeriksa apakah aksi ini pernah dieksekusi sebelumnya. Caranya yaitu dengan memanipulasi variabel hasBeenHandled.

    Awalnya variabel hasBeenHandled bernilai false. Kemudian ketika aksi pertama kali dilakukan nilai hasBeenHandled akan diubah menjadi true. Sedangkan pada aksi selanjutnya ia akan mengembalikan null karena hasBeenHandled telah bernilai true.

    Selain itu, ada juga fungsi peekContent yang bisa Anda gunakan untuk melihat nilai dari content walaupun aksi event sudah dilakukan.

     Menggunakan Event Wrapper
Untuk menggunakan kelas ini cukup mudah. Sesuai namanya, Anda hanya perlu membungkus (wrap) data yang ingin dijadikan single event.


     Anda dapat membungkusnya dengan format Event<TipeData> seperti contoh di atas. Kemudian untuk memasukkan nilai ke dalam variabel tersebut, Anda harus menginisialisasi Event dengan constructor pesan yang ingin dijadikan sebagai content seperti ini untuk menyesuaikan tipenya.

     Lalu untuk mengambil data tersebut, cukup panggil fungsi getContentIfNotHandled seperti ini

     Secara otomatis ketika aksi sudah pernah dilakukan sebelumnya, maka ia akan menghasilkan null.
     */

}