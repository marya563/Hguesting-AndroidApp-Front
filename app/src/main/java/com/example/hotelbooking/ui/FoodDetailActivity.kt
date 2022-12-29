package com.example.hotelbooking.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.hotelbooking.R
import com.example.hotelbooking.api.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food_detail.*
import kotlinx.android.synthetic.main.activity_hotel_information.*

class FoodDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        food_back.setOnClickListener{
            onBackPressed()
        }

        val nameText : TextView = findViewById(R.id.food_name)
        val descriptionText : TextView = findViewById(R.id.delivery_info_txt)
        val priceText : TextView = findViewById(R.id.food_price)
        val imageText : ImageView = findViewById(R.id.food_image)

        val bundle : Bundle?= intent.extras

        val name = bundle!!.getString("name")
        val description = bundle.getString("description")
        val price = bundle.getString("price")
        val image = bundle.getString("image")

        nameText.text = name
        descriptionText.text = description
        priceText.text = price

        Picasso.get()
            .load("${RetrofitInstance.BASE_URL}roomservice/uploads/${image}")
            .placeholder(R.drawable.progress_animation)
            .fit()
            .centerCrop()
            .into(imageText)



    }
}