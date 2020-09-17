package com.example.newsapp.repositories

import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.services.ApiProvider
import retrofit2.Response

class NewsRepository(private val db: ArticleDatabase) {

    // Api calls
    suspend fun getTopHeadlines(category: String, page: Int): Response<NewsResponse> {
        return ApiProvider.retrofit.getTopHeadlines(category = category, page = page)
    }

    suspend fun getSearchQuery(query: String, page: Int): Response<NewsResponse> {
        return ApiProvider.retrofit.getSearchQuery(searchQuery = query, page = page)
    }

    // Room calls
    suspend fun insertArticle(article: Article) = db.getArticleDao().insert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().delete(article)

    fun getSavedArticles() = db.getArticleDao().getSavedArticles()
}