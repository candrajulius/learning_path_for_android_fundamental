package com.candra.latihaninjectionandrepository.di

import android.content.Context
import com.candra.latihaninjectionandrepository.repository.NewsRepository
import com.candra.latihaninjectionandrepository.retrofit.ApiConfig
import com.candra.latihaninjectionandrepository.roomdatabase.NewsDatabase

object Injection {

    fun provideRepository(context: Context): NewsRepository{
        val apiService = ApiConfig.api
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService,dao)
    }
}