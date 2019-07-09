package com.k4dnikov.forecast.data.api.model

import io.realm.RealmObject

open class SysEntity(
    var pod: String? = null
) : RealmObject()