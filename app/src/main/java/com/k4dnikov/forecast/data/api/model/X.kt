package com.k4dnikov.forecast.data.api.model

data class X(
    val clouds: Clouds?,
    val dt: Long?,
    val dt_txt: String?,
    val main: Main?,
    val rain: Rain?,
    val sys: Sys?,
    val weather: List<Weather>?,
    val wind: Wind?
)