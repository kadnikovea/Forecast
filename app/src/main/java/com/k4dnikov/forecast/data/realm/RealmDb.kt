package com.k4dnikov.forecast.data.realm

import com.k4dnikov.forecast.data.api.model.*
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults

class RealmDb {

    private val realm: Realm = Realm.getDefaultInstance()

    fun save(wheatherData: WheatherData) {

        realm.executeTransaction { realm ->

            var wheatherEntity: WheatherEntity = realm.createObject(WheatherEntity::class.java)

            wheatherEntity.cnt = wheatherData?.cnt
            wheatherEntity.cod = wheatherData?.cod
            wheatherEntity.message = wheatherData?.message


            var cityEntity: CityEntity = realm.createObject(CityEntity::class.java)

            cityEntity.country = wheatherData.city?.country
            cityEntity.id = wheatherData.city?.id
            cityEntity.name = wheatherData.city?.name


            var coordEntity: CoordEntity = realm.createObject(CoordEntity::class.java)

            coordEntity.lat = wheatherData?.city?.coord?.lat
            coordEntity.lon = wheatherData?.city?.coord?.lon


            cityEntity.coordEntity = coordEntity

            wheatherEntity.cityEntity = cityEntity


            if (wheatherData != null && wheatherData.list != null)
                for (X in wheatherData?.list) {

                    var xEntity: XEntity = realm.createObject(XEntity::class.java)

                    xEntity.dt = X.dt
                    xEntity.dt_txt = X.dt_txt

                    var cloudsEntity = realm.createObject(CloudsEntity::class.java)

                    cloudsEntity.all = X?.clouds?.all

                    xEntity.cloudsEntity = cloudsEntity

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

                    var rainEntity: RainEntity = realm.createObject(RainEntity::class.java)

                    rainEntity.hour = X?.rain?.hour

                    xEntity.rainEntity = rainEntity

                    var sysEntity: SysEntity = realm.createObject(SysEntity::class.java)

                    sysEntity.pod = X?.sys?.pod

                    xEntity.sysEntity = sysEntity


                    var windEntity: WindEntity = realm.createObject(WindEntity::class.java)

                    windEntity.deg = X?.wind?.deg
                    windEntity.speed = X?.wind?.speed

                    xEntity.windEntity = windEntity


                    if (X != null && X.weather != null)
                        for (w in X?.weather) {

                            var weatherEntity: WeatherEntity = realm.createObject(WeatherEntity::class.java)

                            weatherEntity.description = w?.description
                            weatherEntity.icon = w?.icon
                            weatherEntity.id = w?.id
                            weatherEntity.main = w?.main

                        }

                }

        }

    }

    fun getAll(): Observable<XEntity> {

        val results: RealmResults<WheatherEntity> = realm.where(WheatherEntity::class.java).findAllAsync()


        val resultsList: List<List<XEntity>> = realm.copyFromRealm(results).map { it -> realm.copyFromRealm(it.list) }

        return Observable.fromIterable(resultsList).flatMap { it-> Observable.fromIterable(it)} }





//        Observable.fromIterable(resultsList)
//            .map { t: WheatherEntity ->
//
//                val coord = Coord(
//                    t.cityEntity?.coordEntity?.lat,
//                    t.cityEntity?.coordEntity?.lat
//                )
//
//                val city = City(
//                    coord,
//                    t.cityEntity?.country,
//                    t.cityEntity?.id,
//                    t.cityEntity?.name
//                )
//
//
//                var listX: MutableList<X> = ArrayList()
//
//                if (t.list != null)
//                    for (xEntity in realm.copyFromRealm(t.list)) {
//
//                        val clouds = Clouds(xEntity.cloudsEntity?.all)
//
//                        val main = Main(
//                            xEntity.mainEntity?.grnd_level,
//                            xEntity.mainEntity?.humidity,
//                            xEntity.mainEntity?.pressure,
//                            xEntity.mainEntity?.sea_level,
//                            xEntity.mainEntity?.temp,
//                            xEntity.mainEntity?.temp_kf,
//                            xEntity.mainEntity?.temp_max,
//                            xEntity.mainEntity?.temp_min
//                        )
//
//
//                        val rain = Rain(xEntity.rainEntity?.hour)
//
//                        val sys = Sys(xEntity.sysEntity?.pod)
//
//                        val wind = Wind(
//                            xEntity.windEntity?.deg,
//                            xEntity.windEntity?.speed
//                        )
//
//                        var listWeather: MutableList<Weather> = ArrayList<Weather>()
//
//
//                        if (xEntity.weatherEntity != null)
//                            for (w in realm.copyFromRealm(xEntity.weatherEntity)) {
//
//                                var weather = Weather(w.description,
//                                    w.icon,
//                                    w.id,
//                                    w.main)
//
//                                listWeather.add(weather)
//
//                            }
//
//
//                        var X = X(clouds, xEntity.dt, xEntity.dt_txt, main, rain, sys, listWeather, wind)
//
//                        listX.add(X)
//                    }
//
//
//                val wheatherData = WheatherData(city, t.cnt, t.cod, listX, t.message)
//
//
//            }


    }


