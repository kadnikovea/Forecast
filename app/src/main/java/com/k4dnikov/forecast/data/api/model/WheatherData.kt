package com.k4dnikov.forecast.data.api.model

data class WheatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Double
)