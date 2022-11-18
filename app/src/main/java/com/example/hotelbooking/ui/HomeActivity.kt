package com.example.hotelbooking.ui

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.HotelAdapter
import com.example.hotelbooking.api.RestApiService
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.HotelResponse
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    //    var rv: RecyclerView =findViewById(R.id.recyclerView1)
    //    rv.layoutManager= LinearLayoutManager(this)
    //    val list = ArrayList<Hotel>()
    //    val Hotel1=Hotel("Amazon","United States","15-5-2015","15-5-2015","zrzrg")
    //    list.add(Hotel1)
    //    var adapter=HotelAdapter(list)
    //    rv.adapter=adapter

        val Retrofit = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        Retrofit.getHotel().enqueue(object : Callback<List<Hotel>>{
            override fun onResponse(call: Call<List<Hotel>>, response: Response<List<Hotel>>) {
                showData(response.body()!!)
                //  d("daniel","onResponse:${response.body()!![0]}")
            }

            override fun onFailure(call: Call<List<Hotel>>, t: Throwable) {
                //  d("daniel","onFailure")
            }
        })



    }


    private fun showData(hotels : List<Hotel>){
        recyclerView1.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = HotelAdapter(hotels)
        }
        recyclerView1.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

}