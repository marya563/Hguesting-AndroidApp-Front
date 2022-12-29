package com.example.newapp.API

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

        var BASE_URL = "http://10.0.2.2:3001"
    //var BASE_URL = "http://192.168.1.5:3001"
    fun api(context: Context?) :UserApi {

        val retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttpClient(context)) // Add our Okhttp client
            .build()
        //.create(UserApi::class.java)


        return retrofit.create(UserApi::class.java)
    }
  /*  fun api2( context: Context?) :CarsApi {
        val retrofit2 = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okhttpClient(context)) // Add our Okhttp client
            .build()
       //  .create(Api::class.java)


        return retrofit2.create(CarsApi::class.java)
    } $*/

    private fun okhttpClient(context: Context?): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .build()
    }
}