package com.example.hotelbooking.adapter

import ShoppingCart
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.ServiceRoom
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_service_room_managment.view.*
import kotlinx.android.synthetic.main.food_item_layout.view.*
import kotlinx.android.synthetic.main.itemhotel.view.*

class RoomServiceAdapter( private val serviceRooms : List<ServiceRoom> ,private val onItemClickListener:OnItemClickListener , private val   productListener: ProductListener ): RecyclerView.Adapter<RoomServiceAdapter.ServiceRoomViewHolder>() {
    val cart = mutableListOf<ServiceRoom>()
    interface ProductListener
    {
        fun onProductClicked(serviceRooms: ServiceRoom)
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    inner class ServiceRoomViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        @SuppressLint("LogNotTimber")
        fun bindRoomService(serviceRoom : ServiceRoom , productListener: ProductListener){
            serviceRoom.id=""
            itemView.food_name.text = serviceRoom.name
            itemView.food_price.text = "${serviceRoom.price} \$"
            itemView.food_order.setOnClickListener{
                Log.d("item","CLICKED")
                Log.d("ITEM",serviceRoom.toString())
          //      shoppingCart.addProduct(serviceRoom)
          //      shoppingCart.print()
                productListener.onProductClicked(serviceRoom)
                Toast.makeText(itemView.context, "Added to cart!", Toast.LENGTH_SHORT).show()
            }
            Picasso.get()
                .load("${RetrofitInstance.BASE_URL}roomservice/uploads/${serviceRoom.image}")
                .placeholder(R.drawable.progress_animation)
                .fit()
                .centerCrop()
                .into(itemView.food_image)

        }

        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(position)
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomServiceAdapter.ServiceRoomViewHolder {
        return ServiceRoomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.food_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RoomServiceAdapter.ServiceRoomViewHolder, position: Int) {
        holder.bindRoomService(serviceRooms.get(position), productListener )

    }

    override fun getItemCount(): Int = serviceRooms.size
}