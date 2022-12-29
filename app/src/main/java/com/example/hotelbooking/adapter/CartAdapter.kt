package com.example.hotelbooking.adapter

import ShoppingCart
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.ServiceRoom
import com.makeramen.roundedimageview.RoundedTransformationBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.itemhotel.view.*

class CartAdapter(private val cartClickListener: CartClickListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val items = mutableListOf<ServiceRoom>()


    interface CartClickListener{
        fun onDeleteClicked(serviceRoom: ServiceRoom)
        fun onPlusClicked(serviceRoom: ServiceRoom)
        fun onMinusClicked(serviceRoom: ServiceRoom)
    }

    // Inner class for the view holder
    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.FoodName)
        val priceTextView: TextView = view.findViewById(R.id.FoodPrice)
        val imageTextView: ImageView = view.findViewById(R.id.FoodImage)
        val addQuantityBtn : ImageButton = view.findViewById(R.id.eachCartItemAddQuantityBtn)
        val minusQuantityBtn : ImageButton = view.findViewById(R.id.eachCartItemMinusQuantityBtn)
        val productQuantity : TextView = view.findViewById(R.id.eachCartItemQuantityTV)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        // Inflate the layout for each item in the cart
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.itemcart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val ServiceRoom = items[position]
        // Set the product name and quantity
        holder.nameTextView.text = ServiceRoom.name
        holder.priceTextView.text = " ${ServiceRoom.price} \$"
        holder.productQuantity.setText(ShoppingCart.instance.getTotalQuantityByName(ServiceRoom.name).toString())

        Picasso.get()
            .load("${RetrofitInstance.BASE_URL}roomservice/uploads/${ServiceRoom.image}")
            .placeholder(R.drawable.progress_animation)
            .fit()
            .centerCrop()
            .into(holder.imageTextView)

        holder.addQuantityBtn.setOnClickListener{
            cartClickListener.onPlusClicked(ServiceRoom)
        }

        holder.minusQuantityBtn.setOnClickListener{
            cartClickListener.onMinusClicked(ServiceRoom)
        }


    }

    override fun getItemCount() = items.size

    fun setItems(items: List<ServiceRoom>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}
