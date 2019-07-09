package com.k4dnikov.forecast.data.repository

import com.k4dnikov.forecast.Forecast
import com.k4dnikov.forecast.data.api.model.WheatherData
import com.k4dnikov.forecast.data.api.service.WheathermapApi
import io.reactivex.Observable


class ForecastRepositoryImpl(private val wheathermapApi: WheathermapApi) : ForecastRepository {

    override fun getForecast(): Observable<WheatherData> {

        return wheathermapApi.getWeatherForecast(Forecast.zip)

    }

}