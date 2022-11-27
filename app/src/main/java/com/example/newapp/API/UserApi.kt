package com.example.newapp.API

import com.example.newapp.models.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.RequestBody
import retrofit2.http.*

interface UserApi {
    @POST("signin")
    fun login(@Body user: User): Call<User>


    @POST("signup")
    fun register(@Body user: User): Call<ResponseBody>

    @Multipart
    @POST("uploadProfileImg")
    fun upload( @Part image: MultipartBody.Part): Call<User>


    @GET("users/{id}")
    fun getByIdUserrrr(@Path("id") id : String):Call<User>
}