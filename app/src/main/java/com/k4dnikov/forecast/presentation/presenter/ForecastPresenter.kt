package com.k4dnikov.forecast.presentation.presenter

import com.k4dnikov.forecast.data.repository.ForecastRepository
import com.k4dnikov.forecast.presentation.base.BasePresenter
import com.k4dnikov.forecast.presentation.ui.view.MainActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmChangeListener

class ForecastPresenter(private val forecastRepository: ForecastRepository,
                        private val view: MainActivityView) : BasePresenter() {

    init {
        registerDbChangeListener()
    }

    val compositeDisposable = CompositeDisposable()

    fun getForecast() {

        view.showLoading()

        val disposableCache = forecastRepository.getForecastCache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                view.setDataToAdapter(it)
                view.hideLoading()

            }
            .doOnError {
                view.showError(it.localizedMessage)
            }
            .subscribe()

        val disposableRemote = forecastRepository.getForecastRemote()
            .doOnNext {
            }
            .doOnError {
                view.showError(it.localizedMessage)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        compositeDisposable.addAll(disposableCache, disposableRemote)

    }

    fun registerDbChangeListener(){

        Realm.getDefaultInstance().addChangeListener(object : RealmChangeListener<Realm> {
            override fun onChange(t: Realm?) {

               forecastRepository.getForecastCache()
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .doOnNext {
                       view.setDataToAdapter(it) }
                   .doOnError {
                       view.showError(it.localizedMessage)
                   }
                   .subscribe()

                view.hideLoading()

            }

        })
    }

    fun dispose(){

        compositeDisposable.dispose()

    }

}