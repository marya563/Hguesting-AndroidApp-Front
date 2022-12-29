package com.example.hotelbooking.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class ServiceRoomQuantite(var name : String ,var quantite :Int ,var price :Int): Serializable {
    private constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readInt()!!,
    )
    companion object CREATOR : Parcelable.Creator<ServiceRoomQuantite> {
        override fun createFromParcel(p0: Parcel): ServiceRoomQuantite {
            return ServiceRoomQuantite(p0)
        }

        override fun newArray(p0: Int): Array<ServiceRoomQuantite?> {
            return arrayOfNulls(p0)
        }


    }}
