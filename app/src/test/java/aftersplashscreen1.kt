package com.example.newapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.example.newapp.R

class aftersplashscreen1 : AppCompatActivity() {
    private lateinit var imgview: ImageView
    private lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aftersplashscreen)
        btn = findViewById(R.id.next)


        btn.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )

        }


    } }

