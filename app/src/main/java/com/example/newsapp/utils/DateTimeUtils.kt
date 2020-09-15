package com.example.newsapp.utils

import android.annotation.SuppressLint
import com.snov.timeagolibrary.PrettyTimeAgo
import java.text.SimpleDateFormat

object DateTimeUtils {
    @SuppressLint("SimpleDateFormat")
    fun timestampToTimeAgo(time: String): String {
        val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        return PrettyTimeAgo.getTimeAgo(PrettyTimeAgo.timestampToMilli(time, timeFormat))
    }
}