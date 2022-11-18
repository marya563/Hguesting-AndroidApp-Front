package com.example.hotelbooking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.models.Hotel
import kotlinx.android.synthetic.main.itemhotel.view.*

class HotelAdapter(  private val hotels : List<Hotel> ): RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    class HotelViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bindHotel(hotel : Hotel){
            itemView.title.text = hotel.name
            itemView.country.text = hotel.description
            itemView.price.text = hotel.price

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.itemhotel, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bindHotel(hotels.get(position))
    }

    override fun getItemCount(): Int = hotels.size

}