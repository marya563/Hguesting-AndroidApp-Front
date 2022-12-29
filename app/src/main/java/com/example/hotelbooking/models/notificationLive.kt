package com.example.hotelbooking.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class notificationLive : ViewModel() {
    val notificationLiveData = MutableLiveData<Int>()

    fun addNotification() {
        val currentValue = notificationLiveData.value ?: 0
        notificationLiveData.value = currentValue + 1
    }
}