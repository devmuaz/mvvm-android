package com.example.newsapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repositories.NewsRepository
import com.example.newsapp.services.NewsService
import com.example.newsapp.services.RetrofitSingleton
import com.example.newsapp.utils.NewsApplication
import com.example.newsapp.utils.Resource
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

    fun getBreakingNews() = viewModelScope.launch {
        newsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = RetrofitSingleton.build(NewsService::class.java)
                    .getTopHeadlines(pageNumber = breakingNewsPage)
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
            if (hasInternetConnection()) {
                val response = RetrofitSingleton.build(NewsService::class.java)
                    .searchHeadlines(searchQuery, pageNumber = searchNewsPage)
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

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE,
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}