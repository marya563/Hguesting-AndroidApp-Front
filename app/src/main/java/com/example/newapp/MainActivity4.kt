package com.example.newapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val fragment1:Fragment=ProfileFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,fragment1)
            commit()
        }
    }
}