package com.example.newsapp.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.models.Article

object DifferCallbacks {
    val newsDifferCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}