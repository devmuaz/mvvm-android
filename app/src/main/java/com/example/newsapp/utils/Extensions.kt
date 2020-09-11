package com.example.newsapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun Context.loadImage(url: String?, view: ImageView) = Glide
    .with(this)
    .load(url)
    .thumbnail(0.1F)
    .into(view)