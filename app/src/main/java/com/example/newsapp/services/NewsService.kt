package com.example.newsapp.services

import com.example.newsapp.models.NewsResponse
import com.example.newsapp.utils.Constants.API_KEY
import com.example.newsapp.utils.Constants.categories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") countryCode: String = "us",
        @Query("category") categoryType: String = categories.first(),
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchHeadlines(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<NewsResponse>
}