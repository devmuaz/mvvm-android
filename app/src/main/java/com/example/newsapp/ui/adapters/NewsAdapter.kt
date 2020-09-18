package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleCardBinding
import com.example.newsapp.models.Article
import com.example.newsapp.ui.adapters.DifferCallbacks.newsDifferCallback
import com.example.newsapp.utils.Handler

class NewsAdapter(@LayoutRes private val res: Int = R.layout.item_article_card) :
    RecyclerView.Adapter<NewsViewHolder>() {

    val differ = AsyncListDiffer(this, newsDifferCallback)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemArticleCardBinding = DataBindingUtil.inflate<ItemArticleCardBinding>(
            layoutInflater, res, parent, false
        )
        itemArticleCardBinding.handler = Handler.instance
        return NewsViewHolder(itemArticleCardBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentArticle = differ.currentList[position]
        holder.bind(currentArticle)
    }
}

class NewsViewHolder(binding: ItemArticleCardBinding) : RecyclerView.ViewHolder(binding.root) {
    private val itemBinding = binding

    fun bind(article: Article) {
        itemBinding.article = article
    }
}