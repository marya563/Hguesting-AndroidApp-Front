package com.example.hotelbooking.ui.fragments

import ShoppingCart
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.HotelAdapter
import com.example.hotelbooking.adapter.RoomServiceAdapter
import com.example.hotelbooking.api.RestApiService
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.ServiceRoom
import com.example.hotelbooking.ui.FoodDetailActivity
import com.example.hotelbooking.ui.backend.HotelManagementActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_drinks.*
import kotlinx.android.synthetic.main.fragment_foods.*
import kotlinx.android.synthetic.main.fragment_hotel.*
import kotlinx.android.synthetic.main.fragment_hotel.recyclerView1
import kotlinx.android.synthetic.main.fragment_snacks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SnacksFragment : Fragment(), RoomServiceAdapter.OnItemClickListener , RoomServiceAdapter.ProductListener  {

    interface ShoppingCartListener {
        fun onShoppingCartChanged(shoppingCart: ShoppingCart)
    }

    var snackks = ArrayList<ServiceRoom>()
    private val shoppingCart = ShoppingCart.instance
    private var shoppingCartListener: FoodsFragment.ShoppingCartListener? = null
//    companion object {
//        fun newInstance(shoppingCart: ShoppingCart): SnacksFragment {
//            val fragment = SnacksFragment()
//            fragment.shoppingCart = shoppingCart
//            return fragment
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_snacks, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val Retrofit = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        Retrofit.getRoomService().enqueue(object : Callback<List<ServiceRoom>> {
            @SuppressLint("LogNotTimber")
            override fun onResponse(call: Call<List<ServiceRoom>>, response: Response<List<ServiceRoom>>) {
                showData(response.body()!!)
            }

            override fun onFailure(call: Call<List<ServiceRoom>>, t: Throwable) {
                //  d("daniel","onFailure")
            }
        })

    }

    @SuppressLint("LogNotTimber")
    private fun showData(serviceRooms: List<ServiceRoom>) {
        for (item in serviceRooms) {
            if (item.type == "snack") {
                snackks.add(item)
            }}

        recyclerViewSnack.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RoomServiceAdapter(snackks,this@SnacksFragment,this@SnacksFragment)

        }
        recyclerViewSnack.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

    }

    override fun onItemClick(position: Int) {
        val intent = Intent(context, FoodDetailActivity::class.java)
        Log.d("test",snackks.toString())
        intent.putExtra("name",snackks[position].name)
        intent.putExtra("description",snackks[position].description)
        intent.putExtra("price",snackks[position].price.toString())
        intent.putExtra("image",snackks[position].image)
        startActivity(intent)
    }

    override fun onProductClicked(serviceRooms: ServiceRoom) {
        if (shoppingCart.containsProduct(serviceRooms)){
            Toast.makeText(context, "Already in cart!", Toast.LENGTH_SHORT).show()
        }else {
            shoppingCart.addProduct(serviceRooms)
            shoppingCartListener?.onShoppingCartChanged(shoppingCart)
            Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show()

        }
    }
}