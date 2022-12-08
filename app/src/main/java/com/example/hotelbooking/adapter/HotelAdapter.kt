package com.example.hotelbooking.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.itemhotel.view.*


class HotelAdapter(  private val hotels : List<Hotel> , private val listener: OnItemClickListener ): RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(view : View) : RecyclerView.ViewHolder(view),View.OnClickListener{
        val transformation: Transformation = RoundedTransformationBuilder()
            .cornerRadiusDp(30f)
            .oval(false)
            .build()
        fun bindHotel(hotel : Hotel){
            itemView.title.text = hotel.name.replace("\"", "")
            itemView.country.text = hotel.description.replace("\"", "")
            itemView.price.text = hotel.price.toString()
            Picasso.get()
                .load("${RetrofitInstance.BASE_URL}uploads/${hotel.image}")
                .fit()
                .centerCrop()
                .transform(transformation)
                .into(itemView.img)

        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
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