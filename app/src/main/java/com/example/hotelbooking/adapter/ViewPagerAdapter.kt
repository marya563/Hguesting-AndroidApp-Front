package com.example.hotelbooking.adapter

import ShoppingCart
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hotelbooking.ui.fragments.DrinksFragment
import com.example.hotelbooking.ui.fragments.FoodsFragment
import com.example.hotelbooking.ui.fragments.SnacksFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FoodsFragment()
            1 -> DrinksFragment()
            else -> SnacksFragment()

        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Foods"
            1 -> "Drinks"
            else -> "Snacks"

        }
    }


    override fun getCount(): Int {
        return 3
    }
}