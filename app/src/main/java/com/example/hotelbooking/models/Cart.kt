package com.example.hotelbooking.models

data class Cart(
    val items: MutableMap<Food, Int>,
    var totalCost: Double,
    var discount: Double,
    var tax: Double
)
