package com.example.hotelbooking.ui.fragments

import ShoppingCart
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.HotelAdapter
import com.example.hotelbooking.adapter.RoomServiceAdapter
import com.example.hotelbooking.api.RestApiService
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.ServiceRoom
import com.example.hotelbooking.ui.CartActivity
import com.example.hotelbooking.ui.FoodDetailActivity
import com.example.hotelbooking.ui.HotelInformation
import com.example.hotelbooking.ui.backend.HotelManagementActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_foods.*
import kotlinx.android.synthetic.main.fragment_hotel.*
import kotlinx.android.synthetic.main.fragment_hotel.recyclerView1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FoodsFragment : Fragment() , RoomServiceAdapter.OnItemClickListener, RoomServiceAdapter.ProductListener   {

    interface ShoppingCartListener {
        fun onShoppingCartChanged(shoppingCart: ShoppingCart)
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    var Foodds = ArrayList<ServiceRoom>()
  //  private lateinit var adappter: RoomServiceAdapter

    private lateinit var adapter: RoomServiceAdapter
    private var productList = listOf<ServiceRoom>()
    private val shoppingCart = ShoppingCart.instance
    private var shoppingCartListener: ShoppingCartListener? = null

//    companion object {
//        fun newInstance(shoppingCart: ShoppingCart): FoodsFragment {
//            val fragment = FoodsFragment()
//            fragment.shoppingCart = shoppingCart
//            return fragment
//        }
//    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_foods, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFood)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Retrofit = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        Retrofit.getRoomService().enqueue(object : Callback<List<ServiceRoom>> {
            @SuppressLint("LogNotTimber")
            override fun onResponse(call: Call<List<ServiceRoom>>, response: Response<List<ServiceRoom>>) {
                for (item in response.body()!!) {
                   if (item.type == "food") {
                        Foodds.add(item)
                    }}
                Log.d("test",Foodds.toString())
//
//                recyclerViewFood.apply {
//                    layoutManager = LinearLayoutManager(context)
//                    adapter = RoomServiceAdapter(Foodds,this@FoodsFragment)
//
//                }
//                recyclerViewFood.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)

                productList = response.body()!!
                adapter = RoomServiceAdapter(Foodds,this@FoodsFragment,this@FoodsFragment)
                recyclerViewFood.adapter = adapter


            }

            override fun onFailure(call: Call<List<ServiceRoom>>, t: Throwable) {
                //  d("daniel","onFailure")
            }
        })


        
    }


    @SuppressLint("LogNotTimber")
    private fun showData(serviceRooms: List<ServiceRoom>) {

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


       override fun onItemClick(position: Int) {
        val intent = Intent(context, FoodDetailActivity::class.java)
        intent.putExtra("name",Foodds[position].name)
        intent.putExtra("description",Foodds[position].description)
        intent.putExtra("price",Foodds[position].price.toString())
        intent.putExtra("image",Foodds[position].image)
        startActivity(intent)
   }

}