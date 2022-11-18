package com.example.hotelbooking.api

import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.HotelResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApiService {
    @Headers("Content-Type:application/json")
    @POST("addHotel")
    fun addHotel(@Body info: Hotel): Call<Hotel>

    @Headers("Content-Type:application/json")
    @GET("getHotel")
    fun getHotel(): Call<List<Hotel>>

}


class RetrofitInstance {
    companion object {
        const val BASE_URL: String = "http://192.168.1.6:3001/Hotel/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}