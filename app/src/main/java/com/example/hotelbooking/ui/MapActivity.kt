package com.example.hotelbooking.ui

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hotelbooking.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_hotel_information.*

class MapActivity : AppCompatActivity() {

    lateinit var mapFragment : SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            var addressList: List<Address>? = null
            val geoCoder = Geocoder(this)

            val bundle : Bundle?= intent.extras
            val name = bundle!!.getString("name")
            val adress = bundle.getString("adress")

            addressList = geoCoder.getFromLocationName(adress, 1)
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)

            googleMap.addMarker(MarkerOptions().position(latLng).title(name))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f))
        })

    }
}