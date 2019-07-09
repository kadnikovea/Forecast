package com.k4dnikov.forecast.data.api.model

import io.realm.RealmList
import io.realm.RealmObject

open class XEntity(
    var cloudsEntity: CloudsEntity? = null,
    var dt: Int? = null,
    var dt_txt: String? = null,
    var mainEntity: MainEntity? = null,
    var rainEntity: RainEntity? = null,
    var sysEntity: SysEntity? = null,
    var weatherEntity: RealmList<WeatherEntity>? = null,
    var windEntity: WindEntity? = null
) : RealmObject()