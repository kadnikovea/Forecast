package com.k4dnikov.forecast.data.api.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class RainEntity(@SerializedName("1h") var hour: Double? = null) : RealmObject()