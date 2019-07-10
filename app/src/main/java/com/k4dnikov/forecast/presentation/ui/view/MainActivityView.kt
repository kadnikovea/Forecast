package com.k4dnikov.forecast.presentation.ui.view

import com.k4dnikov.forecast.data.api.model.XEntity
import com.k4dnikov.forecast.presentation.base.BaseView

interface MainActivityView : BaseView {

    fun setDataToAdapter(it: XEntity?)

    fun showLoading()

    fun hideLoading()

}