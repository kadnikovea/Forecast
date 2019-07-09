package com.k4dnikov.forecast.data.api.model

import io.realm.RealmList
import io.realm.RealmObject

open class WheatherEntity(
    var cityEntity: CityEntity? = null,
    var cnt: Int? = null,
    var cod: String? = null,
    var list: RealmList<XEntity>? = null,
    var message: Double? = null
) : RealmObject()