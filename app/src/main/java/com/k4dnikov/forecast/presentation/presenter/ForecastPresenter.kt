package com.k4dnikov.forecast.presentation.presenter

import com.k4dnikov.forecast.data.realm.RealmDb
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

    val compositeDisposable = CompositeDisposable()

    init {
        registerDbChangeListener()
    }


    fun getForecast() {

        view.showLoading()

        val forecastCache = forecastRepository.getForecastCache()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                println("XXXXXXXXX changes came " + it.toString())
                view.setDataToAdapter(it)
                view.hideLoading()

            }
            .subscribe()

        val forecastRemote = forecastRepository.getForecastRemote()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it-> it.list }
            .subscribe({
                RealmDb().save(it)
            })

        compositeDisposable.addAll(forecastCache, forecastRemote)


    }

    fun registerDbChangeListener(){

        Realm.getDefaultInstance().addChangeListener(object : RealmChangeListener<Realm> {
            override fun onChange(t: Realm?) {

               forecastRepository.getForecastCache()
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .doOnNext {
                       println("XXXXXXXXX changes came " + it.toString())
                       view.setDataToAdapter(it) }
                   .subscribe()

                view.hideLoading()


            }

        })
    }

    fun dispose(){

        compositeDisposable.clear()

    }


}