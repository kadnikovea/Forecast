package com.k4dnikov.forecast.data.api.model

import io.realm.RealmList
import io.realm.RealmObject

open class HourForecastEntity(
    var dt: Long? = null,
    var mainEntity: MainEntity? = null,
    var weatherEntity: RealmList<WeatherEntity>? = null
) : RealmObject()