package com.k4dnikov.forecast.data.repository

import com.k4dnikov.forecast.data.api.model.WheatherData
import com.k4dnikov.forecast.data.api.model.HourForecast
import com.k4dnikov.forecast.data.api.model.HourForecastEntity
import io.reactivex.Observable

interface ForecastRepository {

    fun getForecastRemote(): Observable<WheatherData>

    fun getForecastCache(): Observable<HourForecastEntity>

}