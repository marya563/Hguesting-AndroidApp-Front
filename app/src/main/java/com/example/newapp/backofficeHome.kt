package com.example.newapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class backofficeHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backofficehome)
        val addcar = findViewById<Button>(R.id.Addcar)
        val addEvent = findViewById<Button>(R.id.AddEvent)
        val addservice = findViewById<Button>(R.id.Addservice)


        addcar.setOnClickListener {
            val intent = Intent(this, addcarr::class.java)


            startActivity(intent)
        }
    }
}