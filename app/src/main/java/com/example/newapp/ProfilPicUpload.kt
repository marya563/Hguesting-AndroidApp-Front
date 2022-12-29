package com.example.newapp


import android.content.Intent

import com.example.newapp.API.RetrofitInstance
import com.example.newapp.models.User
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
//import androidx.lifecycle.lifecycleScope
//import com.example.newapp.databinding.ActivityProfilePicUploadBinding
import okhttp3.MultipartBody
//import id.zelory.compressor.Compressor
import android.widget.Toast
import androidx.cardview.widget.CardView
//import id.zelory.compressor.constraint.format
//import id.zelory.compressor.constraint.quality
//import id.zelory.compressor.constraint.size
import okhttp3.RequestBody
//import androidx.activity.viewModels
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import com.airbnb.lottie.LottieAnimationView
import retrofit2.Response


class ProfilPicUpload : AppCompatActivity()  {



    companion object {
        private val PERMISSION_CODE = 1_000
    }
    private var selectedImageUri: Uri? = null
    var ivImage: ImageView?=null
    lateinit var cvImage: CardView
    lateinit var btnUpload: Button
    lateinit var profile : Button
    lateinit var pbLoading: LottieAnimationView
   //lateinit var binding: ActivityProfilePicUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  binding = ActivityProfilePicUploadBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_profilpic_upload)

        cvImage = findViewById(R.id.cvImage)
        ivImage = findViewById(R.id.ivImage)
        btnUpload = findViewById(R.id.btnUpload)
        profile = findViewById(R.id.profile)
        pbLoading = findViewById(R.id.pbLoading)
        pbLoading.visibility = View.GONE


        cvImage.setOnClickListener {

            pickImageFromGallery()

        }
        btnUpload.setOnClickListener {

            startUpload()
        }

        profile.setOnClickListener{
            navigatee()
        }

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PERMISSION_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_CODE && resultCode == RESULT_OK) {
            selectedImageUri = data?.data

            ivImage?.setImageURI(selectedImageUri)

        }
    }

    override fun onBackPressed() {
        //disable back button
    }
    //The compressing process is starting here.





    private fun startUpload() {
        pbLoading.visibility = View.VISIBLE
        if (selectedImageUri == null) {
            println("image null")

            return
        }


        val stream = contentResolver.openInputStream(selectedImageUri!!)
        println("-------------------------------------" + stream)
        val request =
            stream?.let {
                RequestBody.create(
                    "image/jpg".toMediaTypeOrNull(),
                    it.readBytes()
                )
            } // read all bytes using kotlin extension
        val image = request?.let {
            MultipartBody.Part.createFormData(
                "image",
                "image.jpg",
                it
            )
        }
        val apiInterface = RetrofitInstance.api(this)

        if (image?.body != null) {

            println("++++++++++++++++++++++++++++++++++++" + image)

            apiInterface.upload( image).enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    Log.w("Url",response.raw().request.url.toString())
                    if (response.isSuccessful) {
                        Log.i("onResponse goooood", response.body().toString())
                        pbLoading.visibility = View.GONE
                        val toast =
                            Toast.makeText(
                                applicationContext,
                                "ok!",
                                Toast.LENGTH_SHORT)
                        toast.show()
                        navigate()


                    } else {
                        Log.i("OnResponse not good", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    //progress_bar.progress = 0
                    val toast =
                        Toast.makeText(
                            applicationContext,
                            "cnx error!",
                            Toast.LENGTH_SHORT)
                    toast.show()
                    println("noooooooooooooooooo")
                }

            })
        }
        else{
            val toast =
                Toast.makeText(
                    applicationContext,
                    "choisir une image!",
                    Toast.LENGTH_SHORT)
            toast.show()
        }


    }

    private fun navigate(){
        val mainIntent = Intent(this, ProfileFragment::class.java)
        startActivity(mainIntent)
    }

    private fun navigatee(){
        val mainIntent = Intent(this, MainActivity4::class.java)
        startActivity(mainIntent)




    }}





