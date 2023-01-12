package com.candra.latihaninjectionandrepository.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.candra.latihaninjectionandrepository.entity.NewsEntity
import com.candra.latihaninjectionandrepository.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel()
{
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    /*
    bookmarkState bernilai true untuk fungsi saveNews dan bernilai false untuk fungsi deleteNews.
     */
    fun saveNews(news: NewsEntity){
        viewModelScope.launch {
            newsRepository.setBookmarkedNews(news,true)
        }
    }

    fun deleteNews(news: NewsEntity){
        viewModelScope.launch {
            newsRepository.setBookmarkedNews(news,false)
        }
    }
}