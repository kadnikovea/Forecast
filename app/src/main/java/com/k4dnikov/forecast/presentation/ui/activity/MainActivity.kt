package com.k4dnikov.forecast.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.k4dnikov.forecast.R
import com.k4dnikov.forecast.data.api.model.HourForecast
import com.k4dnikov.forecast.data.api.model.HourForecastEntity
import com.k4dnikov.forecast.data.api.service.WheathermapApi
import com.k4dnikov.forecast.data.realm.RealmDb
import com.k4dnikov.forecast.data.repository.ForecastRepositoryImpl
import com.k4dnikov.forecast.presentation.base.BaseActivity
import com.k4dnikov.forecast.presentation.presenter.ForecastPresenter
import com.k4dnikov.forecast.presentation.ui.adapter.ForecastRecyclerAdapter
import com.k4dnikov.forecast.presentation.ui.view.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loader_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : BaseActivity(), MainActivityView, KodeinAware {

    override val kodein by closestKodein()

    private val whatherApi: WheathermapApi by instance()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var forecastAdapter: ForecastRecyclerAdapter

    private lateinit var presenter: ForecastPresenter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewForecast.layoutManager = linearLayoutManager

        forecastAdapter = ForecastRecyclerAdapter(this@MainActivity)
        recyclerViewForecast.adapter = forecastAdapter

        val forecastRepository = ForecastRepositoryImpl(whatherApi, RealmDb())

        presenter = ForecastPresenter(forecastRepository, this)

        presenter.getForecast()

    }

    override fun setDataToAdapter(it: HourForecastEntity?) {

        if (it != null) {
            forecastAdapter.addData(it)
            forecastAdapter.notifyDataSetChanged()
        }

    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }


}
