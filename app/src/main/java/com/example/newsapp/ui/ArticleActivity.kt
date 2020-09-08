package com.example.newsapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article
import com.example.newsapp.repositories.NewsRepository
import com.example.newsapp.viewmodels.NewsViewModel
import com.example.newsapp.viewmodels.NewsViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.activity_article.articleRootLayout
import kotlinx.android.synthetic.main.activity_main.*

class ArticleActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var article: Article
    private var canRemoveArticle: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository, application)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        intent.let {
            article = it.getSerializableExtra("article") as Article
            canRemoveArticle = it.getBooleanExtra("canRemoveArticle", false)
        }
        title = article.source.name
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        canRemoveArticle?.let {
            if (it) {
                menuInflater.inflate(R.menu.delete_article_menu, menu)
            } else {
                menuInflater.inflate(R.menu.save_article_menu, menu)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveArticleMenuItem -> saveArticle()
            R.id.deleteArticleMenuItem -> deleteArticle()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveArticle() {
        viewModel.saveArticle(article)
        Snackbar.make(articleRootLayout.rootView,
            "Article Saved Successfully.",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun deleteArticle() {
        viewModel.deleteArticle(article)
        finish()
    }
}