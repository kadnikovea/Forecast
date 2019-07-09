package com.k4dnikov.forecast.data.repository

import com.k4dnikov.forecast.data.api.model.WheatherData
import io.reactivex.Observable

interface ForecastRepository {

    fun getForecast() : Observable<WheatherData>

}