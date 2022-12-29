package com.example.hotelbooking.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.HotelAdapter
import com.example.hotelbooking.api.RestApiService
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.ui.HotelInformation
import com.example.hotelbooking.ui.HotelMainActivity
import com.example.hotelbooking.ui.backend.HotelManagementActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.recyclerView1
import kotlinx.android.synthetic.main.fragment_hotel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelFragment : Fragment(), HotelAdapter.OnItemClickListener {

    var hotells = ArrayList<Hotel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Retrofit = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        Retrofit.getHotel().enqueue(object : Callback<List<Hotel>> {
            override fun onResponse(call: Call<List<Hotel>>, response: Response<List<Hotel>>) {
                showData(response.body()!!)
                //  d("daniel","onResponse:${response.body()!![0]}")
            }

            override fun onFailure(call: Call<List<Hotel>>, t: Throwable) {
                //  d("daniel","onFailure")
            }
        })

        imageView15.setOnClickListener {
            val intent = Intent(requireContext(), HotelManagementActivity::class.java)
            startActivity(intent)
        }

    }


    private fun showData(hotels : List<Hotel>){
        recyclerView1.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = HotelAdapter(hotels,this@HotelFragment)
            Log.d("test",hotels.toString())
            hotells = hotels as ArrayList<Hotel>

        }
        recyclerView1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(context, HotelInformation::class.java)
        Log.d("test",hotells.toString())
        intent.putExtra("name",hotells[position].name)
        intent.putExtra("adress",hotells[position].adress)
        intent.putExtra("description",hotells[position].description)
        intent.putExtra("price",hotells[position].price.toString())
        intent.putExtra("image",hotells[position].image)
        startActivity(intent)

    }

}