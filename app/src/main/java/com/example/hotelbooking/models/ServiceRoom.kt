package com.example.hotelbooking.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class ServiceRoom (
    var id : String,
    var name : String,
    var type : String,
    var description : String,
    var price : Number,
    var image :String
) : Serializable {
    private constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,

    )

//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(p0: Parcel, p1: Int) {
//        p0.writeString(id)
//        p0.writeString(name)
//        p0.writeString(type)
//        p0.writeString(description)
//        p0.writeInt(price.toInt())
//        p0.writeString(image)
//
//    }

    companion object CREATOR : Parcelable.Creator<ServiceRoom> {
        override fun createFromParcel(p0: Parcel): ServiceRoom {
            return ServiceRoom(p0)
        }

        override fun newArray(p0: Int): Array<ServiceRoom?> {
            return arrayOfNulls(p0)
        }


    }}