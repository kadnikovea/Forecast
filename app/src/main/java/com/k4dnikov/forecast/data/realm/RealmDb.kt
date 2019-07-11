package com.k4dnikov.forecast.data.realm

import com.k4dnikov.forecast.data.api.model.MainEntity
import com.k4dnikov.forecast.data.api.model.WeatherEntity
import com.k4dnikov.forecast.data.api.model.HourForecast
import com.k4dnikov.forecast.data.api.model.HourForecastEntity
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.Sort

class RealmDb {

    private val realm: Realm = Realm.getDefaultInstance()

    fun save(hourForecastList: List<HourForecast>?) {

        realm.executeTransaction { realm ->

            if (hourForecastList != null)

                for (X in hourForecastList) {

                    var hourEntity: HourForecastEntity = realm.createObject(HourForecastEntity::class.java)

                    hourEntity.dt = X.dt

                    var mainEntity = realm.createObject(MainEntity::class.java)

                    mainEntity.grnd_level = X.main?.grnd_level
                    mainEntity.humidity = X.main?.humidity
                    mainEntity.pressure = X.main?.pressure
                    mainEntity.sea_level = X.main?.sea_level
                    mainEntity.temp = X.main?.temp
                    mainEntity.temp_kf = X.main?.temp_kf
                    mainEntity.temp_max = X.main?.temp_max
                    mainEntity.temp_min = X.main?.temp_min

                    hourEntity.mainEntity = mainEntity

                    val weatherList: RealmList<WeatherEntity> = RealmList<WeatherEntity>()

                    if (X.weather != null)
                        for (w in X.weather) {

                            val weatherEntity: WeatherEntity = realm.createObject(WeatherEntity::class.java)

                            weatherEntity.description = w.description
                            weatherEntity.icon = w.icon
                            weatherEntity.id = w.id
                            weatherEntity.main = w.main

                            weatherList.add(weatherEntity)

                        }

                    hourEntity.weatherEntity = weatherList

                }

        }

    }

    fun getAll(): Observable<HourForecastEntity> {

        val results: RealmResults<HourForecastEntity> = realm.where(HourForecastEntity::class.java).sort("dt", Sort.DESCENDING).findAll()

        val res = realm.copyFromRealm(results)

        return Observable.fromIterable(res)


    }

    fun close(){
        realm.close()
    }

}


