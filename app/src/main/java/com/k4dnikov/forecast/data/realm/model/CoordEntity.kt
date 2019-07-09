package com.k4dnikov.forecast.data.api.model

import io.realm.RealmObject

open class CoordEntity(
    var lat: Double? = null,
    var lon: Double? = null
) : RealmObject()