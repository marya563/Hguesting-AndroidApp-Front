package com.example.hotelbooking.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.hotelbooking.R
import com.example.hotelbooking.api.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hotel_information.*
import kotlinx.android.synthetic.main.itemhotel.view.*

class HotelInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_information)
        setSupportActionBar(toolbar)

        val nameText : TextView = findViewById(R.id.textView5)
        val descriptionText : TextView = findViewById(R.id.textView24)
        val priceText : TextView = findViewById(R.id.textView37)
        val imageText : ImageView = findViewById(R.id.hotelimage)

        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val description = bundle.getString("description")
        val price = bundle.getString("price")
        val image = bundle.getString("image")

        nameText.text = name
        descriptionText.text = description
        priceText.text = price
        Picasso.get()
            .load("${RetrofitInstance.BASE_URL}uploads/${image}")
            .fit()
            .centerCrop()
            .into(imageText)


    }
}