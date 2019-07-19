package com.k4dnikov.forecast.data.repository

import com.k4dnikov.forecast.Forecast
import com.k4dnikov.forecast.data.api.model.HourForecastEntity
import com.k4dnikov.forecast.data.api.model.WheatherData
import com.k4dnikov.forecast.data.api.service.WeatherMapApi
import com.k4dnikov.forecast.data.realm.RealmDb
import io.reactivex.Observable


class ForecastRepositoryImpl(private val weatherMapApi: WeatherMapApi,
                             private val realmDb: RealmDb) : ForecastRepository {


    override fun getForecastCache(): Observable<HourForecastEntity> {

        return realmDb.getAll()

    }

    override fun getForecastRemote(): Observable<WheatherData> {


       return weatherMapApi.getWeatherForecast(Forecast.zip)

    }




}