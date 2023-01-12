package com.candra.latihaninjectionandrepository.retrofit

import com.candra.latihaninjectionandrepository.entity.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=id&category=science")
   suspend fun getNews(
        @Query("apiKey") apiKey: String
    ): NewsResponse
}