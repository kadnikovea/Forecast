package com.k4dnikov.forecast.data.realm

import com.k4dnikov.forecast.data.api.model.MainEntity
import com.k4dnikov.forecast.data.api.model.WeatherEntity
import com.k4dnikov.forecast.data.api.model.X
import com.k4dnikov.forecast.data.api.model.XEntity
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults

class RealmDb {

    private val realm: Realm = Realm.getDefaultInstance()

    fun save(xList: List<X>?) {

        realm.executeTransaction { realm ->

            if (xList != null)

                for (X in xList) {

                    var xEntity: XEntity = realm.createObject(XEntity::class.java)

                    xEntity.dt = X.dt

                    var mainEntity = realm.createObject(MainEntity::class.java)

                    mainEntity.grnd_level = X?.main?.grnd_level
                    mainEntity.humidity = X?.main?.humidity
                    mainEntity.pressure = X?.main?.pressure
                    mainEntity.sea_level = X?.main?.sea_level
                    mainEntity.temp = X?.main?.temp
                    mainEntity.temp_kf = X?.main?.temp_kf
                    mainEntity.temp_max = X?.main?.temp_max
                    mainEntity.temp_min = X?.main?.temp_min

                    xEntity.mainEntity = mainEntity

                    var weatherList: RealmList<WeatherEntity> = RealmList<WeatherEntity>()

                    if (X != null && X.weather != null)
                        for (w in X?.weather) {

                            var weatherEntity: WeatherEntity = realm.createObject(WeatherEntity::class.java)

                            weatherEntity.description = w?.description
                            weatherEntity.icon = w?.icon
                            weatherEntity.id = w?.id
                            weatherEntity.main = w?.main

                            weatherList.add(weatherEntity)

                        }

                    xEntity.weatherEntity = weatherList

                }

        }

    }

    fun getAll(): Observable<XEntity> {

        val results: RealmResults<XEntity> = realm.where(XEntity::class.java).findAllAsync()

        val res = realm.copyFromRealm(results)


        return Observable.fromIterable(res)


    }

}


