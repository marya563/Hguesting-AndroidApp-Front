package com.example.newapp

import CarFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class Carmain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carmain)
        val fragment2: Fragment=CarFragment()


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment2)
            commit()
        }
    }
}