package com.k4dnikov.forecast.presentation.presenter

import com.k4dnikov.forecast.data.repository.ForecastRepository
import com.k4dnikov.forecast.presentation.base.BasePresenter
import com.k4dnikov.forecast.presentation.ui.view.MainActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmChangeListener

class ForecastPresenter(private val forecastRepository: ForecastRepository,
                        private val view: MainActivityView) : BasePresenter() {

    init {
        registerDbChangeListener()
    }


    fun getForecast() {

        view.showLoading()

        forecastRepository.getForecastCache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                view.setDataToAdapter(it)
                view.hideLoading()

            }
            .subscribe()

        forecastRepository.getForecastRemote()
            .doOnNext {
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    fun registerDbChangeListener(){

        Realm.getDefaultInstance().addChangeListener(object : RealmChangeListener<Realm> {
            override fun onChange(t: Realm?) {

               forecastRepository.getForecastCache()
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .doOnNext {
                       view.setDataToAdapter(it) }
                   .subscribe()

                view.hideLoading()


            }

        })
    }


}