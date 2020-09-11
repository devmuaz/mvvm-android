package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.models.Article
import com.example.newsapp.ui.ArticleActivity
import com.example.newsapp.utils.loadImage
import com.example.newsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as ArticleActivity).viewModel
        article = (activity as ArticleActivity).article

        initArticleData(article)

        readMoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_articleFragment_to_webArticleFragment)
        }
    }

    private fun initArticleData(article: Article) {
        context?.loadImage(article.urlToImage, articleImage)
        articleTitle.text = article.title
        articleDescription.text = article.description
        articleContent.text = article.content
    }
}