package com.k4dnikov.forecast.data.api.model

import io.realm.RealmObject

open class CloudsEntity(
    var all: Int? = null
) : RealmObject()