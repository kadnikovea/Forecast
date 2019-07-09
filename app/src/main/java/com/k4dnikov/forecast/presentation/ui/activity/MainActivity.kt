package com.k4dnikov.forecast.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.k4dnikov.forecast.Forecast
import com.k4dnikov.forecast.R
import com.k4dnikov.forecast.data.api.service.WheathermapApi
import com.k4dnikov.forecast.data.repository.ForecastRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var forecastRepository = ForecastRepositoryImpl(createApi())

        forecastRepository.getForecast().su


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
