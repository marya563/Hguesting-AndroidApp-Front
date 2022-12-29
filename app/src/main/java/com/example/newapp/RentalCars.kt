/* package com.example.newapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.Adapters.Car2Adapter
import com.example.newapp.Adapters.CarAdapter
import java.text.FieldPosition

class RentalCars  : AppCompatActivity(){
      private var layoutManager: RecyclerView.LayoutManager?= null
    private var layoutManager2: RecyclerView.LayoutManager?= null
    lateinit var recyclerView : RecyclerView
    lateinit var recyclerView2 : RecyclerView
    //lateinit var Rentbt : Button
       private var adapter: RecyclerView.Adapter<CarAdapter.ViewHolder>? = null
    private var adapter2: RecyclerView.Adapter<Car2Adapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_rentalcars)
       // Rentbt = findViewById(R.id.recyclerView)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView2 = findViewById(R.id.recyclerView2)
        layoutManager = LinearLayoutManager(this)
        layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView2.layoutManager = layoutManager2
        adapter = CarAdapter()
        adapter2 = Car2Adapter()
        recyclerView.adapter= adapter
        recyclerView2.adapter= adapter2
        /*recycler.layoutManager
        adapter = CarAdapter()
       */
        var adapter = CarAdapter()
        recyclerView.adapter = adapter
       adapter.setOnItemClickListener(object : CarAdapter.onItemClickListener{
           override fun onItemClick(position: Int) {
               val intent = Intent(this@RentalCars,AddCar::class.java)
               //Toast.makeText(this@RentalCars, "you clicked on item n.$position",Toast.LENGTH_SHORT).show()
          startActivity(intent)
           }


       })
}
} */