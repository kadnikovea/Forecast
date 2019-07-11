package com.k4dnikov.forecast

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.k4dnikov.forecast.data.api.service.WeatherMapApi
import com.k4dnikov.forecast.data.realm.RealmDb
import io.realm.Realm
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Forecast : Application(), KodeinAware {

    companion object{
        const val BASE_URL = "https://samples.openweathermap.org/data/2.5/"
        const val APPID = "b6907d289e10d714a6e88b30761fae22"
        const val APPID_QUERY_NAME = "appid"
        const val zip = "94040"
    }

    override fun onCreate() {
        super.onCreate()

        Realm.init(this@Forecast)
    }


    override val kodein by Kodein.lazy{

        bind<RealmDb>() with singleton { RealmDb() }

        bind<Gson>() with singleton { GsonBuilder()
            .create()}

        bind<OkHttpClient>() with singleton { OkHttpClient.Builder()
            .addInterceptor(instance<Interceptor>())
            .build() }

        bind<Interceptor>() with singleton {
            object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val originalRequest = chain!!.request()
                    val originalUrl = originalRequest!!.url()

                    val newUrl = originalUrl!!.newBuilder()!!
                        .addQueryParameter(APPID_QUERY_NAME, APPID)!!
                        .build()

                    val requestBuilder = originalRequest.newBuilder()!!
                        .url(newUrl)

                    val newRequest = requestBuilder!!.build()

                    return chain.proceed(newRequest)
                }
            } }

        bind<GsonConverterFactory>() with singleton { GsonConverterFactory.create(instance<Gson>()) }

        bind<RxJava2CallAdapterFactory>() with singleton { RxJava2CallAdapterFactory.create() }

        bind<Retrofit>() with singleton { Retrofit.Builder()
            .client(instance<OkHttpClient>())
            .addConverterFactory(instance<GsonConverterFactory>())
            .addCallAdapterFactory(instance<RxJava2CallAdapterFactory>())
            .baseUrl(BASE_URL)
            .build() }

        bind<WeatherMapApi>() with singleton { instance<Retrofit>().create(WeatherMapApi::class.java) }

    }


}