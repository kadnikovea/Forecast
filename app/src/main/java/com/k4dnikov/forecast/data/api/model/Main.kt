package com.k4dnikov.forecast.data.api.model

data class Main(
    val grnd_level: Double?,
    val humidity: Int?,
    val pressure: Double?,
    val sea_level: Double?,
    val temp: Double?,
    val temp_kf: Double?,
    val temp_max: Double?,
    val temp_min: Double?
)