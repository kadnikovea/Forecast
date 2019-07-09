package com.k4dnikov.forecast.data.api.model

import io.realm.RealmObject

open class CityEntity(
    var coordEntity: CoordEntity? = null,
    var country: String? = null,
    var id: Int? = null,
    var name: String? = null
) : RealmObject()