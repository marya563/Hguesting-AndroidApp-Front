package com.example.newapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class ProfileActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.profil)
        //    lateinit var edit: Button
            val fragment2: Fragment =updateProfileFragment()

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container,fragment2)
                commit()
            }

        }
    }
