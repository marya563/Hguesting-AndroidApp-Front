package com.example.newapp.Adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.R

class RecommendedAdapter: RecyclerView.Adapter<RecommendedAdapter.ViewHolder>(){
    private var title =  arrayOf("Emerald Hotel","Hotel el louz")
    private var images = intArrayOf(
        R.drawable.hotel3,
        R.drawable.hotel2,

    )
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val v = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recommended_item, parent, false)
        return ViewHolder(itemView)
    }
    override fun getItemCount(): Int{
        return title.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.item_title.text = title[position]
        holder.item_Image2.setImageResource(images[position])

    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var item_Image2: ImageView
        var item_title: TextView
        init {
            item_Image2 = itemView.findViewById(R.id.item_Image2)
            item_title = itemView.findViewById(R.id.item_title)

        }

            /* inner class MainHolder(var binding: CarAdapter): RecyclerView.ViewHolder(binding.true) {
                 init {
                     binding.root.setOnClickListener {
                         onItemClick?.invoke()
                     }
                 }

                 val itemImage = binding.images
                 val item_title = binding.title
                 val item_price = binding.prices
                 val item_details = binding.details
                 val item_engine = binding.engine

             }*/


        }

    }
