package com.example.hotelbooking.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hotelbooking.R
import com.example.hotelbooking.api.RestApiService
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelManagementActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var description: EditText
    private lateinit var price: EditText
    private lateinit var rooms: EditText

    private lateinit var btnadd : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_management)

        val btnadd = findViewById<Button>(R.id.btnadd)

        btnadd.setOnClickListener {

            val name = findViewById<EditText>(R.id.name)
            val description = findViewById<EditText>(R.id.description)
            val price = findViewById<EditText>(R.id.price)
            val rooms = findViewById<EditText>(R.id.rooms)

            addHotel(name.text.toString(), description.text.toString(), price.text.toString(), rooms.text.toString())
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
        }
        }

    private fun addHotel(name: String, description: String, price: String, rooms: String) {

        val Retrofit = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val id : String = ""
        val HotelInfo = Hotel(id, name, description, price, rooms)

        Retrofit.addHotel(HotelInfo).enqueue(object : Callback<Hotel> {
            override fun onResponse(call: Call<Hotel>, response: retrofit2.Response<Hotel>) {
                Toast.makeText(this@HotelManagementActivity,"Successfully Added",Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<Hotel>, t: Throwable) {
                Toast.makeText(this@HotelManagementActivity,"Failed to add Item",Toast.LENGTH_SHORT).show()
            }

        })


    }

}