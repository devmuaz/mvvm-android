package com.example.newsapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import com.snov.timeagolibrary.PrettyTimeAgo
import com.snov.timeagolibrary.PrettyTimeAgo.getTimeAgo
import kotlinx.android.synthetic.main.item_article_card.view.*
import java.text.SimpleDateFormat

class NewsAdapter(@LayoutRes private val res: Int = R.layout.item_article_card) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    // View Holder
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // Differ callback
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return NewsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentArticle = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(currentArticle.urlToImage).into(articleImage)
            articleTitle.text = currentArticle.title

            // Converting DateTime format to TimeAgo format
            articleDateTime?.text = timestampToTimeAgo(currentArticle.publishedAt)

            // adding a click listener to each item
            setOnClickListener { onItemClickListener?.let { it(currentArticle) } }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun timestampToTimeAgo(time: String): String {
        val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return getTimeAgo(PrettyTimeAgo.timestampToMilli(time, timeFormat))
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun onItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}