package com.example.hotelbooking.ui

import ShoppingCart
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.CartAdapter
import com.example.hotelbooking.models.CartViewModel
import com.example.hotelbooking.models.ServiceRoom
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity(), CartAdapter.CartClickListener {

    private lateinit var cart: List<ServiceRoom>
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter
    private val shoppingCart = ShoppingCart.instance

    @SuppressLint("LogNotTimber")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        imageView7.setOnClickListener{
            onBackPressed()
        }

  //      val intent = intent
//        shoppingCart = intent.getSerializableExtra("shopping_cart") as ShoppingCart

//         cart = intent.getParcelableArrayListExtra("cart")!!

        val recyclerView = findViewById<RecyclerView>(R.id.cart_recycler_view)
        val totalPrix  = findViewById<TextView>(R.id.cartActivityTotalPriceTv)

        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(this)
        recyclerView.adapter = cartAdapter

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        cartViewModel.shoppingCartLiveData.observe(this, Observer { shoppingCart -> cartAdapter.setItems(shoppingCart.getItems())
        })
        cartViewModel.shoppingCartLiveData.observe(this, Observer {
            totalPrix.setText(ShoppingCart.instance.TotalPrice().toString()+"$")

        })

    }

    override fun onDeleteClicked(serviceRoom: ServiceRoom) {
        TODO("Not yet implemented")
    }

    @SuppressLint("LogNotTimber")
    override fun onPlusClicked(serviceRoom: ServiceRoom) {
        val totalPrix  = findViewById<TextView>(R.id.cartActivityTotalPriceTv)

        val i = shoppingCart.getTotalQuantityByName(serviceRoom.name)
        val quantity = shoppingCart.updateQuantity(serviceRoom.name,i+1)
        shoppingCart.updateQuantity(serviceRoom.name , i+1)
        shoppingCart.updatePrice(serviceRoom.name , quantity*serviceRoom.price.toInt())
        cartAdapter.notifyDataSetChanged()
        cartViewModel.shoppingCartLiveData.observe(this, Observer {
            totalPrix.setText(ShoppingCart.instance.TotalPrice().toString()+"$")

        })

    }

    @SuppressLint("LogNotTimber")
    override fun onMinusClicked(serviceRoom: ServiceRoom) {
        val totalPrix  = findViewById<TextView>(R.id.cartActivityTotalPriceTv)

        val i = shoppingCart.getTotalQuantityByName(serviceRoom.name)
        if (i>1) {
            val quantity = shoppingCart.updateQuantity(serviceRoom.name, i - 1)
            shoppingCart.updateQuantity(serviceRoom.name, i - 1)
            shoppingCart.updatePrice(serviceRoom.name, quantity*serviceRoom.price.toInt())
            cartAdapter.notifyDataSetChanged()
            cartViewModel.shoppingCartLiveData.observe(this, Observer {
                totalPrix.setText(ShoppingCart.instance.TotalPrice().toString()+"$")

            })
        }

    }
}