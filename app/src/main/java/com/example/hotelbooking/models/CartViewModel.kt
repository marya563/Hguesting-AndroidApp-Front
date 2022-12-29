package com.example.hotelbooking.models

import androidx.lifecycle.ViewModel

class CartViewModel: ViewModel() {

    val shoppingCartLiveData = ShoppingCart.instance.getShoppingCartLiveData()
}