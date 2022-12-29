package com.example.hotelbooking.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import com.example.hotelbooking.R
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.mapbox.api.geocoding.v5.MapboxGeocoding
import com.mapbox.api.geocoding.v5.models.GeocodingResponse
import com.mapbox.common.LifecycleService
import com.mapbox.common.TileStoreOptions.MAPBOX_ACCESS_TOKEN
// import com.mapbox.geocoder.MapboxGeocoder
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationPlugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.gestures.gestures
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.json.JSONObject
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import kotlinx.android.synthetic.main.activity_hotel_management.*
import java.util.regex.Pattern

class MapBoxActivity : AppCompatActivity() {
    var mapView: MapView?= null
    var annotationApi : AnnotationPlugin? = null
    lateinit var annotaionConfig : AnnotationConfig
    val layerID = "map_annotation";
    var pointAnnotationManager : PointAnnotationManager? = null
    var markerList :ArrayList<PointAnnotationOptions> = ArrayList()

    // Just for testing
    var latitudeList : ArrayList<Double> = ArrayList()
    var longitudeList : ArrayList<Double> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_box)
        mapView = findViewById<View>(R.id.mapView) as MapView
        createLatLongForMarker();



        mapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    zoomCamera();
                    //  addOnMapClickListener(this@HomeFragmentNew)

                    annotationApi = mapView?.annotations
                    annotaionConfig = AnnotationConfig(
                        layerId = layerID
                    )
                    pointAnnotationManager = annotationApi?.createPointAnnotationManager(annotaionConfig)!!

                    createMarkerList();


                    try{
                        mapView!!.gestures.pitchEnabled = true
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }})







        // It will not work when we call addClick listener on OnCreate
        /*pointAnnotationManager?.addClickListener(OnPointAnnotationClickListener { annotation: PointAnnotation ->
            onMarkerItemClick(annotation)
            true
        })*/

    }

    private fun zoomCamera(){

    }

    private fun createLatLongForMarker(){

    }





    private fun createMarkerList(){

        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val adress = bundle.getString("adress")

        clearAnnotation();

        // It will work when we create marker
        pointAnnotationManager?.addClickListener(OnPointAnnotationClickListener { annotation: PointAnnotation ->
            onMarkerItemClick(annotation)
            true
        })


        markerList =  ArrayList();
        var bitmpa = convertDrawableToBitmap(AppCompatResources.getDrawable(this, R.drawable.marker))

        val mapboxGeocoding = MapboxGeocoding.builder()
            .accessToken("sk.eyJ1IjoiZmVkaWFiZGVuYWJpIiwiYSI6ImNsYnY5a2NqMzAxYWozcmxpZzBicWJvdjAifQ.H5oF8lx-xyf6k0eUibiTIA")
            .query(adress.toString())
            .build()

        mapboxGeocoding.enqueueCall(object : Callback<GeocodingResponse>{

            @SuppressLint("LogNotTimber")
            override fun onResponse(call: Call<GeocodingResponse>, response:
            Response<GeocodingResponse>) {
                val results = response.body()!!.features()
                if (results.size > 0) {

                    // Log the first results Point.
                    val firstResultPoint = results[0].center()
                    latitudeList.add(firstResultPoint!!.latitude())
                    longitudeList.add(firstResultPoint!!.longitude())
                    markerList =  ArrayList();
                    var mObe = JSONObject();
                    mObe.put(name,1);
                    Log.d("aefaf", latitudeList.toString())

                    val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                        .withPoint(Point.fromLngLat(firstResultPoint!!.longitude(),firstResultPoint!!.latitude()))
                        .withData(Gson().fromJson(mObe.toString(), JsonElement::class.java))
                        .withIconImage(bitmpa!!)

                    markerList.add(pointAnnotationOptions);

                    pointAnnotationManager?.create(markerList)

                    mapView!!.getMapboxMap().setCamera(
                        CameraOptions.Builder()
                            .center(Point.fromLngLat(firstResultPoint!!.longitude(),firstResultPoint!!.latitude()))
                            .zoom(11.0)
                            .pitch(55.0)
                            .bearing(-45.0)
                            .build()
                    )


                } else {

                    // No result for your request were found.
                    Log.d("TAG", "onResponse: No result found")

                }
            }

            override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })



//
//            Log.d("aefaf", longitudeList.toString())
//            Log.d("aefae", latitudeList.toString())
//
//            var mObe = JSONObject();
//            mObe.put("somekey",i);
//            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
//                .withPoint(Point.fromLngLat(longitudeList.get(i),latitudeList.get(i)))
//                .withData(Gson().fromJson(mObe.toString(), JsonElement::class.java))
//                .withIconImage(bitmpa!!)
//            markerList.add(pointAnnotationOptions);
//        }
//
//        pointAnnotationManager?.create(markerList)

    }

    fun clearAnnotation(){
        markerList = ArrayList();
        pointAnnotationManager?.deleteAll()
    }

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    fun extractSubstring(input: String): String? {
        val pattern = Pattern.compile("\"([^\"]+)\"")
        val matcher = pattern.matcher(input)
        return if (matcher.find()) {
            matcher.group(1)
        } else {
            null
        }
    }

    private fun onMarkerItemClick(marker: PointAnnotation) {

        val jsonElement: JsonElement? = marker.getData()
        val adress = extractSubstring(jsonElement.toString())

        AlertDialog.Builder(this)
            .setTitle("Hotel Booking")
            .setMessage("Welcome To "+adress.toString())
            .setPositiveButton(
                "OK"
            ) { dialog, whichButton ->
                dialog.dismiss()
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.dismiss() }.show()

    }
}