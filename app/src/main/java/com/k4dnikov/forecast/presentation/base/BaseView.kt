package com.k4dnikov.forecast.presentation.base

open interface BaseView {

    fun showError(error: Int)

    fun showError(error: String)

}