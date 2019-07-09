package com.k4dnikov.forecast.data.api.model

import io.realm.RealmObject

open class WindEntity(
    var deg: Double? = null,
    var speed: Double? = null
) : RealmObject()