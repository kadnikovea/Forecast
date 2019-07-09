package com.k4dnikov.forecast.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View{
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Context.recyclerDateFormat(timestamp: Long): String{
    val timestamp = Timestamp(timestamp)
    val date = Date(timestamp.time)
    val timePattern = "dd.MMMM.yyyyг HH:mm"
    var format = SimpleDateFormat(timePattern)
    return format.format(date)
}