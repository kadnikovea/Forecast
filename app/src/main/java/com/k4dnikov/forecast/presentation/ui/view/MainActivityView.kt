package com.k4dnikov.forecast.presentation.ui.view

import com.k4dnikov.forecast.data.api.model.HourForecast
import com.k4dnikov.forecast.data.api.model.HourForecastEntity
import com.k4dnikov.forecast.presentation.base.BaseView

interface MainActivityView : BaseView {

    fun setDataToAdapter(it: HourForecastEntity?)

    fun showLoading()

    fun hideLoading()

}