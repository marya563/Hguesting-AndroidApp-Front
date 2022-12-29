package com.example.hotelbooking.ui.fragments

import ShoppingCart
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.RoomServiceAdapter
import com.example.hotelbooking.adapter.ViewPagerAdapter
import com.example.hotelbooking.ui.CartActivity
import com.example.hotelbooking.ui.backend.ServiceRoomManagmentActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_foods.*
import kotlinx.android.synthetic.main.fragment_hotel.*
import kotlinx.android.synthetic.main.fragment_hotel.imageView15
import kotlinx.android.synthetic.main.fragment_service_room.*
import kotlinx.android.synthetic.main.fragment_service_room.view.*


class ServiceRoomFragment : Fragment() , FoodsFragment.ShoppingCartListener   {

    private lateinit var adapter: RoomServiceAdapter
    private lateinit var shoppingCart: ShoppingCart
    private lateinit var toolbar: Toolbar
    private lateinit var cartMenuItem: MenuItem
    private lateinit var menuItem: MenuItem
    private lateinit var badgeCounter: TextView
    private var pendingNotifications = 0
 //   private var notificationLiveData = MutableLiveData<Int>().apply { value = 0 }
    private lateinit var Badge :BadgeDrawable


    interface ShoppingCartListener {
        fun onShoppingCartChanged(shoppingCart: ShoppingCart)
    }

    private var shoppingCartListener: ShoppingCartListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShoppingCartListener) {
            shoppingCartListener = context
        } else {
            throw RuntimeException("$context must implement ShoppingCartListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        shoppingCartListener = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_service_room, container, false)

        val viewPager = view.findViewById<ViewPager>(R.id.food_viewpager)
        viewPager.adapter = ViewPagerAdapter(childFragmentManager)

        val tabLayout = view.findViewById<TabLayout>(R.id.food_tab)
        tabLayout.setupWithViewPager(viewPager)

         val viewCartButton = view.findViewById<ImageView>(R.id.food_cart)
        setHasOptionsMenu(true)
        toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
//        val button = view.findViewById<MaterialButton>(R.id.button)
//        val badge = BadgeDrawable.create(requireContext().applicationContext)
//        badge.setNumber(5)
//        Badge.isVisible=true
//       val button = view.findViewById<MaterialButton>(R.id.button)
//        val badge = button.getOrCreateBadgeDrawable()


//        Badge = BadgeDrawable.create(requireContext().applicationContext)
//        Badge.isVisible=true
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.nav_notification -> {
                    // Handle the shopping cart action
                    val intent = Intent(activity, CartActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

//        toolbar.menu.findItem(R.id.cart).let {
//            attachBadgeDrawable(Badge, toolbar)
//        }

        viewCartButton.setOnClickListener {
            val intent = Intent(activity, CartActivity::class.java)
            startActivity(intent)
        }

        return view

    }


    @SuppressLint("UnsafeOptInUsageError")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        notificationLiveData.observe(viewLifecycleOwner, Observer {
//            updateBadgeCounter(it)
//        })

        imageView15.setOnClickListener {
            val intent = Intent(requireContext(), ServiceRoomManagmentActivity::class.java)
            startActivity(intent)
        }



//        attachBadgeDrawable(Badge,toolbar,R.id.nav_notification)

    }

    @SuppressLint("LogNotTimber")
    override fun onShoppingCartChanged(shoppingCart: ShoppingCart) {
        Log.d("shoppingCart",shoppingCart.toString())
        this.shoppingCart = shoppingCart
        shoppingCartListener?.onShoppingCartChanged(shoppingCart)
       // notificationLiveData.value = shoppingCart.getTotalQuantity()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_notification, menu)

        menuItem = menu.findItem(R.id.nav_notification)

//        if (notificationLiveData.value  == 0) {
//            menuItem.actionView = null
//        } else {
//            menuItem.setActionView(R.layout.notification_badge)
//            val view: View = menuItem.actionView!!
//            badgeCounter = view.findViewById(R.id.badge_counter)
//            badgeCounter.text = notificationLiveData.value.toString()
//        }
        menuItem.setOnMenuItemClickListener {
            val intent = Intent(activity, CartActivity::class.java)
            startActivity(intent)
            true
        }
    }

    private fun updateBadgeCounter(count: Int) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        notificationLiveData.observe(viewLifecycleOwner, Observer {
//            updateBadgeCounter(it)
//        })
    }

    private fun updateCartBadge(count: Int) {
        // Set the badge on the cart menu item
//        ActionItemBadge.update(activity, cartMenuItem, R.drawable.ic_baseline_shopping_cart_24, ActionItemBadge.BadgeStyles.RED, count)
    }

}


