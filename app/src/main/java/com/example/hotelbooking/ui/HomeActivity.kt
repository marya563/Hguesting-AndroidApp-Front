package com.example.hotelbooking.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.HotelAdapter
import com.example.hotelbooking.api.RestApiService
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.HotelResponse
import com.example.hotelbooking.ui.fragments.HotelFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() , HotelAdapter.OnItemClickListener {

    var hotells = ArrayList<Hotel>()

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


        var nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        nav.setOnNavigationItemSelectedListener { item ->

            when(item.itemId){
                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.explore ->{
                    startActivity(Intent(this, HotelManagementActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.settings ->{return@setOnNavigationItemSelectedListener true}
                R.id.search ->{return@setOnNavigationItemSelectedListener true}
            }

            false
        }


    }



    private fun showData(hotels : List<Hotel>){
        recyclerView1.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = HotelAdapter(hotels,this@HomeActivity)
            Log.d("test",hotels.toString())
            hotells = hotels as ArrayList<Hotel>

        }
        recyclerView1.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this,"Item $position clicked " , Toast.LENGTH_SHORT).show()
        val intent = Intent(this,HotelInformation::class.java)
        Log.d("test",hotells.toString())
        intent.putExtra("name",hotells[position].name.replace("\"", ""))
        intent.putExtra("adress",hotells[position].adress.replace("\"", ""))
        intent.putExtra("description",hotells[position].description.replace("\"", ""))
        intent.putExtra("price",hotells[position].price.toString())
        intent.putExtra("image",hotells[position].image)
        startActivity(intent)

    }

}