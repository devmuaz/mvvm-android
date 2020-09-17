package com.example.newsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.NewsApplication
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repositories.NewsRepository
import com.example.newsapp.utils.Constants.categories
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.hasInternetConnection
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository, app: Application) :
    AndroidViewModel(app) {

    private val newsDataTemp = MutableLiveData<Resource<NewsResponse>>()
    val newsData = MutableLiveData<Resource<NewsResponse>>()
    private var breakingNewsPage = 1
    private var searchNewsPage = 1

    init {
        getBreakingNews()
    }

    fun getBreakingNews(category: String = categories.first()) = viewModelScope.launch {
        newsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection<NewsApplication>()) {
                val response = newsRepository.getTopHeadlines(category, breakingNewsPage)
                if (response.isSuccessful) {
                    newsDataTemp.postValue(Resource.Success(response.body()!!))
                    newsData.postValue(Resource.Success(response.body()!!))
                } else {
                    newsData.postValue(Resource.Error(response.message()))
                }
            } else {
                newsData.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            newsData.postValue(Resource.Error(t.message!!))
        }
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        newsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection<NewsApplication>()) {
                val response = newsRepository.getSearchQuery(searchQuery, searchNewsPage)
                if (response.isSuccessful) {
                    newsData.postValue(Resource.Success(response.body()!!))
                } else {
                    newsData.postValue(Resource.Error(response.message()))
                }
            } else {
                newsData.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            newsData.postValue(Resource.Error(t.message!!))
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun getSavedArticles() = newsRepository.getSavedArticles()

    fun onSearchClose() {
        newsData.postValue(newsDataTemp.value)
    }
}