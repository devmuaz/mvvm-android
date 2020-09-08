package com.example.newsapp.repositories

import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article

class NewsRepository(private val db: ArticleDatabase) {

    suspend fun insertArticle(article: Article) = db.getArticleDao().insert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().delete(article)

    fun getSavedArticles() = db.getArticleDao().getSavedArticles()
}