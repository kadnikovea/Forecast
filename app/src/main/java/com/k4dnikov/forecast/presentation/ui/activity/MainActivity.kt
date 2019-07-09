package com.k4dnikov.forecast.presentation.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.k4dnikov.forecast.Forecast
import com.k4dnikov.forecast.R
import com.k4dnikov.forecast.data.api.model.XEntity
import com.k4dnikov.forecast.data.api.service.WheathermapApi
import com.k4dnikov.forecast.data.realm.RealmDb
import com.k4dnikov.forecast.data.repository.ForecastRepositoryImpl
import com.k4dnikov.forecast.presentation.base.BaseActivity
import com.k4dnikov.forecast.presentation.presenter.ForecastPresenter
import com.k4dnikov.forecast.presentation.ui.adapter.ForecastRecyclerAdapter
import com.k4dnikov.forecast.presentation.ui.view.MainActivityView
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : BaseActivity(), MainActivityView {



    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var forecastAdapter: ForecastRecyclerAdapter

    private lateinit var presenter: ForecastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerViewForecast.layoutManager = linearLayoutManager

        forecastAdapter = ForecastRecyclerAdapter()
        recyclerViewForecast.adapter = forecastAdapter


        val forecastRepository = ForecastRepositoryImpl(createApi(), RealmDb())

        presenter = ForecastPresenter(forecastRepository, this)

        presenter.getForecast()

//        progress_bar.visibility = View.VISIBLE

//        forecastRepository.getForecastRemote()
//            .doOnNext {
//                println("XXXXXXXXX " + it.toString())
//            }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
//
//
//        Realm.getDefaultInstance().addChangeListener(object : RealmChangeListener<Realm> {
//            override fun onChange(t: Realm?) {
//                val db = RealmDb()
//
//                println("XXXXXXXXX changes came")
//
//                db.getAll()
//            }
//
//        })

    }



    override fun setDataToAdapter(it: XEntity?) {

        if (it != null) {
            println("XXXXXXXXX setDataToAdapter not NULL")
            forecastAdapter.addData(it)
            forecastAdapter.notifyDataSetChanged()

        }

    }

    fun createApi(): WheathermapApi {

        val gson = GsonBuilder().create()

        val apiKeyInterceptor = object : Interceptor {

            override fun intercept(chain: Interceptor.Chain?): Response {
                val originalRequest = chain!!.request()
                val originalUrl = originalRequest!!.url()

                val newUrl = originalUrl!!.newBuilder()!!
                    .addQueryParameter(Forecast.APPID_QUERY_NAME, Forecast.APPID)!!
                    .build()

                val requestBuilder = originalRequest.newBuilder()!!
                    .url(newUrl)

                val newRequest = requestBuilder!!.build()

                return chain.proceed(newRequest)

            }
        }

        val client =  OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()


        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Forecast.BASE_URL)
            .build()
            .create(WheathermapApi::class.java)



    }
}
