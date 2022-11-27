package com.example.newapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val actionBar = supportActionBar


        button1.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)


            startActivity(intent)
        }
        button2.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)

            startActivity(intent)
        }

    }
}