package com.candra.latihaninjectionandrepository.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.candra.latihaninjectionandrepository.entity.NewsDao
import com.candra.latihaninjectionandrepository.entity.NewsEntity
import com.candra.latihaninjectionandrepository.helper.Constant.API_KEY
import com.candra.latihaninjectionandrepository.helper.Result
import com.candra.latihaninjectionandrepository.retrofit.ApiService

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
)
{


    // Digunakan untuk menggabungkan banyak sumber data dalam sebuah LiveData
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading) //1. Inisiasi Loading
        try {
            val response = apiService.getNews(API_KEY)// 2. Mendapatkan response
            val articles = response.articles // Mengambil data yang ingin digunakan
            val newList = articles.map { articlesItem ->
                //3. Mengecek apakah data yang ada sudah ada didalam bookmark atau belom
                val isBookmarked = newsDao.isNewsBookmarked(articlesItem.title)

                //4. Mengubah data response menjadi entity sebelum di masukkan kedalam database
                NewsEntity(
                    articlesItem.title,
                    articlesItem.publishedAt,
                    articlesItem.urlToImage,
                    articlesItem.url,
                    isBookmarked
                )
                //-----------------------------------------------------------------------
            }
            //5. Menghapus semua data dari database yang tidak ditandai bookmark
            newsDao.deleteAll()

            // 6. Memasukkan data baru dari internet ke dalam database
            newsDao.inserNews(newList)
        }catch (ex: Exception){
            Log.d(TAG, "getHeadlineNews: ${ex.message.toString()}")
            emit(Result.Error(ex.message.toString()))
        }
        // Mengambil data dari database yang merupakan sumber utama untuk dikonsumsi dan memberi tanda sukses
        val localData: LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map {Result.Success(it)}
        emitSource(localData)
    }

    // Membaca data yang sudah ada di database
    fun getBookmarkedNews(): LiveData<List<NewsEntity>> = newsDao.getBookmarkedNews()

    // Untuk mensetDatanya
   suspend fun setBookmarkedNews(news: NewsEntity,bookmarkState: Boolean){
        // Jika data nya sudah ada di favorite maka buat bookmarkStatenya true
        // Jika data nya mau dihapus maka tekan false
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object{
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
        ): NewsRepository = instance ?: synchronized(this){
            instance ?: NewsRepository(apiService,newsDao)
        }.also { instance = it }

        private const val TAG = "NewsRepository"
    }

    /*
    Perbedaan emit() dan emitSource()

    emit -> Apabila data sumber bukan merupakan LiveData gunakan fungsi ini
    emitSource -> Apabila data sumber berupa LiveData gunakan fungsi emitSource
     */

    /*
    Persamaan MediatorLiveData dengan LiveData block dapat digunakan
    untuk membuat LiveData baru maupun menggabungkan LiveData yang sudah ada
     */

}