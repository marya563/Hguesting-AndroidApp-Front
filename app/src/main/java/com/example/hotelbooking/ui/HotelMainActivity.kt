package com.example.hotelbooking.ui

import ShoppingCart
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hotelbooking.R
import com.example.hotelbooking.databinding.ActivityHotelMainBinding
import com.example.hotelbooking.ui.fragments.CarFragment
import com.example.hotelbooking.ui.fragments.EventFragment
import com.example.hotelbooking.ui.fragments.HotelFragment
import com.example.hotelbooking.ui.fragments.ServiceRoomFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_hotel_main.*
import kotlinx.android.synthetic.main.notificationbadge_text.view.*


class HotelMainActivity : AppCompatActivity() ,ServiceRoomFragment.ShoppingCartListener {

    private lateinit var binding: ActivityHotelMainBinding
    private lateinit var textView: TextView
//    private lateinit var notificationBadges: View
//    private var count: Int = 1
//    private lateinit var shoppingCart: ShoppingCart



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

//        UpdateCount.setOnClickListener{
//            updateBadgeCount(count++)
//        }


        replaceFragment(HotelFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HotelFragment())
                R.id.explore -> replaceFragment(ServiceRoomFragment())
                R.id.settings -> replaceFragment(EventFragment())
                R.id.search -> replaceFragment(CarFragment())

                else -> {}
            }
            true
        }

    }

//    private fun updateBadgeCount (count: Int = 0){
//
//        val itemView =  binding.bottomNavigationView.getChildAt(1) as? BottomNavigationItemView
//
//        notificationBadges = layoutInflater.inflate(R.layout.notificationbadge_text , itemView , true )
//
//        notificationBadges?.notification_badge?.text = count.toString()
//
//        bottomNavigationView?.addView(notificationBadges)
//    }


    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragments_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onShoppingCartChanged(shoppingCart: ShoppingCart) {
        textView.text = "Shopping cart: ${shoppingCart.getProducts()} items"
    }


}