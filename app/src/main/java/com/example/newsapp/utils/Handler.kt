package com.example.newsapp.utils

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.newsapp.models.Article
import com.example.newsapp.ui.ArticleActivity

class Handler {

    companion object {
        val instance = Handler()
    }

    fun onArticleClicked(view: View, article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
            putBoolean("canRemoveArticle", false)
        }
        val intent = Intent(view.context, ArticleActivity::class.java)
        intent.putExtras(bundle)
        view.context.startActivity(intent)
    }
}
