package com.example.newapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.SharedPreferences
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import  com.example.newapp.R
import com.example.newapp.API.RetrofitInstance
import com.example.newapp.models.User
import com.example.newapp.API.RetrofitInstance.BASE_URL
import com.example.newapp.ID
//import com.esprit.takwira.ui.PREF_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    lateinit var profilename : TextView
    lateinit var email :  TextView
    lateinit var phone :  TextView
    lateinit var location :  TextView
    lateinit var imageView: ImageView
    lateinit var logtoutimg: ImageButton



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        profilename = rootView.findViewById(R.id.user_profile_name)
        email = rootView.findViewById(R.id.profileEmail)
        phone = rootView.findViewById(R.id.profilePhone)
        location = rootView.findViewById(R.id.profileLocation)
        imageView = rootView.findViewById(R.id.user_profile_photo)
        logtoutimg = rootView.findViewById(R.id.logtoutimg)

       // mSharedPref = requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
      // val idUser: String = .getString(ID, null).toString()

        val apiInterface = RetrofitInstance.api(context)
        apiInterface.getByIdUserrrr(ID).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "Connexion Problem", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.w("Url",response.raw().request.url.toString())
                Log.w("body",response.body().toString())


                if (response.isSuccessful){
                    val user : User = response.body()!!
                    Log.w("image url", user.firstname!!)
                    profilename.text = user.firstname+" "+user.lastName
                    email.text = user.email
                    val image = user.profilePic?.substringAfter("upload\\images\\")
                    Log.w("image url", user.profilePic!!)
                    Glide.with(this@ProfileFragment).load("http://"+ BASE_URL+":3001/$image").into(imageView)
                }
            }


        })


        logtoutimg.setOnClickListener{
            requireActivity().finish()
        }
        return rootView
    }


}