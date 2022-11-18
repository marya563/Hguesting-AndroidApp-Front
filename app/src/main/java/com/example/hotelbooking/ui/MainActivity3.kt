package com.example.hotelbooking.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.hotelbooking.R

class MainActivity3 : AppCompatActivity() {
    private lateinit var imgview: ImageView
    private lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        btn = findViewById(R.id.BtnNext)
        imgview = findViewById(R.id.imageView);
        imgview.setImageResource(R.drawable.img_4);

        btn.setOnClickListener{
            startActivity(
                Intent(this, HotelManagementActivity::class.java)
            )

        }


    }
}