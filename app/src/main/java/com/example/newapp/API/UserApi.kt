package com.example.newapp.API

import com.example.newapp.models.Car
import com.example.newapp.models.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.*

interface UserApi {
    @POST("user/signin")
    fun login(@Body user: User): Call<User>


    @POST("user/signup")
    fun register(@Body user: User): Call<ResponseBody>

    @Multipart
    @POST("user/uploadProfileImg")
    fun upload( @Part image: MultipartBody.Part): Call<User>


    @GET("user/getByIdUserrrr/{id}")
    fun getByIdUserrrr(@Path("id") id : String):Call<User>

    @PUT("user/updateUserrById/{id}")
    fun updateUserrById(@Path("id") id : String): Call<User>

    @GET("Car/getCar")
    fun getCar(): Call<List<Car>>
    @POST("/getOrderId")
    fun getOrderId(@Body map: HashMap<String , String>): Call<Order>
    @POST("/updateTransactionStatus")
    fun updateTransactionStatus(@Body map:HashMap<String, String>): Call<String>

}