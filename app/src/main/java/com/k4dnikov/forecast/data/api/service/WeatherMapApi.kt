package com.k4dnikov.forecast.data.api.service

import com.k4dnikov.forecast.data.api.model.WheatherData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherMapApi {
    @GET("forecast/hourly")
    fun getWeatherForecast(@Query("zip") zip:String) : Observable<WheatherData>

}
