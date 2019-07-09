package com.k4dnikov.forecast.data.api.model

import io.realm.RealmObject

open class MainEntity(
    var grnd_level: Double? = null,
    var humidity: Int? = null,
    var pressure: Double? = null,
    var sea_level: Double? = null,
    var temp: Double? = null,
    var temp_kf: Double? = null,
    var temp_max: Double? = null,
    var temp_min: Double? = null
) : RealmObject()