package com.k4dnikov.forecast.data.repository

import com.k4dnikov.forecast.Forecast
import com.k4dnikov.forecast.data.api.model.WheatherData
import com.k4dnikov.forecast.data.api.model.WheatherEntity
import com.k4dnikov.forecast.data.api.model.X
import com.k4dnikov.forecast.data.api.model.XEntity
import com.k4dnikov.forecast.data.api.service.WheathermapApi
import com.k4dnikov.forecast.data.realm.RealmDb
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ForecastRepositoryImpl(private val wheathermapApi: WheathermapApi,
                             private val realmDb: RealmDb) : ForecastRepository {


    override fun getForecastCache(): Observable<XEntity> {

        return realmDb.getAll();

    }

    override fun getForecastRemote(): Observable<WheatherData> {

        wheathermapApi.getWeatherForecast(Forecast.zip)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                realmDb.save(it)

            })

        return wheathermapApi.getWeatherForecast(Forecast.zip)

    }




}