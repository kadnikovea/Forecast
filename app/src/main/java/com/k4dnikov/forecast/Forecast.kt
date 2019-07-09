package com.k4dnikov.forecast

import android.app.Application
import com.google.gson.GsonBuilder
import com.k4dnikov.forecast.data.api.service.WheathermapApi
import com.k4dnikov.forecast.data.realm.RealmDb
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with
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

     override val kodein = Kodein.lazy{

//        //        https://samples.openweathermap.org/data/2.5/forecast/hourly?zip=94040&appid=b6907d289e10d714a6e88b30761fae22
//        constant(tag = "appid") with "b6907d289e10d714a6e88b30761fae22"
        constant(tag = "zip") with "94040"

        import(androidXModule(this@Forecast))

        bind<RealmDb>() with singleton { RealmDb() }

        bind<WheathermapApi>("api") with singleton {

            val gson = GsonBuilder().create()

            val apiKeyInterceptor = object : Interceptor {

                override fun intercept(chain: Interceptor.Chain?): Response {
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
            }

            val client =  OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .build()


            Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(WheathermapApi::class.java)


        }


    }



}