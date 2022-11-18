package com.example.hotelbooking.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class HotelResponse(

    @SerializedName("results")
    val Hotels : List<Hotel>

)

