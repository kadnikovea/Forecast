package com.k4dnikov.forecast.data.api.model

import io.realm.RealmObject

open class WeatherEntity(
    var description: String? = null,
    var icon: String? = null,
    var id: Int? = null,
    var main: String? = null
) : RealmObject()