package com.example.newsapp.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.example.newsapp.R

fun <T : Application> AndroidViewModel.hasInternetConnection(): Boolean {
    val connectivityManager = getApplication<T>().getSystemService(
        Context.CONNECTIVITY_SERVICE,
    ) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun ImageView.loadImage(url: String?) = Glide
    .with(this)
    .load(url)
    .placeholder(R.drawable.ic_downloadable)
    .error(R.drawable.ic_error)
    .into(this)

