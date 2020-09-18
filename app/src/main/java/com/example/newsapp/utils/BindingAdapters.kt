package com.example.newsapp.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.snov.timeagolibrary.PrettyTimeAgo
import java.text.SimpleDateFormat

@BindingAdapter("imageUrl")
fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
    imageView.loadImage(imageUrl)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("timeAgoFormat")
fun convertToTimeAgoFormat(textView: TextView, time: String) {
    val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val timeAgo = PrettyTimeAgo.getTimeAgo(PrettyTimeAgo.timestampToMilli(time, timeFormat))
    textView.text = timeAgo
}