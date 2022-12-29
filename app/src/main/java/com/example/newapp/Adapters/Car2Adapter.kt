package com.example.newapp.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.R

class Car2Adapter: RecyclerView.Adapter<Car2Adapter.ViewHolder>(){

    private var cartype = arrayOf("Suv","Standard", "Prestige")
    private var cartypenum = arrayOf("56","22","32" )
    private var Imagee = intArrayOf(
        R.drawable.range,
        R.drawable.kia,
        R.drawable.mercedes,
    )
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.car_item2, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int{
        return cartype.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.itemcartype_num.text = cartypenum[position]
        holder.item_Image2.setImageResource(Imagee[position])
        holder.item_cartype.text = cartype[position]
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var item_cartype: TextView
        var itemcartype_num: TextView
        var item_Image2: ImageView
        init {
            item_cartype = itemView.findViewById(R.id.item_cartype)
            itemcartype_num = itemView.findViewById(R.id.itemcartype_num)
            item_Image2 = itemView.findViewById(R.id.item_Image2)

        }
    }
}