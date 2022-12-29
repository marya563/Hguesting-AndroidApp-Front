package com.example.hotelbooking.ui

import android.content.Intent
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
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val nameText : TextView = findViewById(R.id.textView5)
        val adressText : TextView = findViewById(R.id.textView21)
        val descriptionText : TextView = findViewById(R.id.textView24)
        val priceText : TextView = findViewById(R.id.textView37)
        val imageText : ImageView = findViewById(R.id.hotelimage)
        val map : ImageView = findViewById(R.id.imageView37)


        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val adress = bundle.getString("adress")
        val description = bundle.getString("description")
        val price = bundle.getString("price")
        val image = bundle.getString("image")
        supportActionBar!!.title=name
        nameText.text = name
        descriptionText.text = description
        priceText.text = price
        adressText.text = adress
        Picasso.get()
            .load("${RetrofitInstance.BASE_URL}Hotel/uploads/${image}")
            .placeholder(R.drawable.progress_animation)
            .fit()
            .centerCrop()
            .into(imageText)


        map.setOnClickListener{
            val intent = Intent(this, MapBoxActivity::class.java)
            intent.putExtra("name",name)
            intent.putExtra("adress",adress)
            startActivity(intent)

        }


    }
}