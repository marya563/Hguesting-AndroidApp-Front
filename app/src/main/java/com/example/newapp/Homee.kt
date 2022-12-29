package com.example.newapp

import CarAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.Adapters.Car2Adapter
import com.example.newapp.Adapters.RecommendedAdapter
import com.example.newapp.Adapters.ServicesAdapter
import com.example.newapp.models.Car
import com.google.android.material.bottomnavigation.BottomNavigationView

import java.text.FieldPosition
class Homee : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager?= null
   // private var layoutManager2: RecyclerView.LayoutManager?= null
    lateinit var recyclerView : RecyclerView
 //   lateinit var recyclerView2 : RecyclerView
    private var adapter: RecyclerView.Adapter<CarAdapter.CarViewHolder>? = null
   // private var adapter2: RecyclerView.Adapter<RecommendedAdapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homee)
        recyclerView = findViewById(R.id.recyclerView)
       // recyclerView2 = findViewById(R.id.recyclerView2)
        layoutManager = LinearLayoutManager(this)
      //  layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
       // recyclerView2.layoutManager = layoutManager2
       // adapter2 = RecommendedAdapter()
       // recyclerView2.adapter= adapter2
       // val add = findViewById<Button>(R.id.add)
    //    lateinit var carList: ArrayList<Car>

       var carList = listOf<Car>()
        val car = carList
       val adapter = CarAdapter(carList)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : CarAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@Homee,Carmain::class.java)
                //Toast.makeText(this@RentalCars, "you clicked on item n.$position",Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }


        })



      } }
