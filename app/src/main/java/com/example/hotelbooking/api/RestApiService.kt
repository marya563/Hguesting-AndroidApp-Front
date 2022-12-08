package com.example.hotelbooking.api

import com.example.hotelbooking.models.Hotel
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RestApiService {


    @Multipart
    @POST("addHotel")
    fun addHotel(
        @Part("id") id: String,
        @Part("name") name: String,
        @Part("description") description: String,
        @Part("price") price: Number,
        @Part("rooms") rooms: Number,
        @Part image: MultipartBody.Part
    ): Call<Hotel>


    @GET("getHotel")
    fun getHotel(): Call<List<Hotel>>


}


class RetrofitInstance {
    companion object {
        const val BASE_URL: String = "http://192.168.1.2:3001/Hotel/"

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