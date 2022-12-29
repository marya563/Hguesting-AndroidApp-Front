package com.example.newapp

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newapp.API.RetrofitInstance
import com.example.newapp.models.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    lateinit var email :  TextView
    lateinit var Name : TextView
    lateinit var lastname : TextView
    lateinit var mSharedPref: SharedPreferences
    lateinit var logtoutimg : ImageButton
    lateinit var imageView : ImageView
    lateinit var edit: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val edit = ProfileFragment.findViewById<Button>(R.id.edit)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState:Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val layoutManager= LinearLayoutManager(context)

        Name = view.findViewById(R.id.profileName)
        lastname= view.findViewById(R.id.plastname)
        email = view.findViewById(R.id.profileEmail)
        imageView = view.findViewById(R.id.user_profile_photo)
        logtoutimg = view.findViewById(R.id.logtoutimg)
        edit = view.findViewById(R.id.edit)


        mSharedPref = requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
       val idUser: String = mSharedPref.getString(ID,null).toString()

        val apiInterface = RetrofitInstance.api(context)
        println(idUser)
        apiInterface.getByIdUserrrr(idUser).enqueue(object : Callback<User> {

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "Connexion Problem", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.w("Url",response.raw().request.url.toString())
                Log.w("body",response.body().toString())


                if (response.isSuccessful){
                    val user : User = response.body()!!
                    email.text = user.email
                   Name.text = user.firstname
                   lastname.text = user.lastname
                    val image = user.profilePic?.substringAfter("uploads\\")
                    Log.w("image url", user.profilePic!!)
                    Glide.with(this@ProfileFragment).load("$image").into(imageView)
                }
            }


        })

        fun updatee(){
            val mainIntent = Intent(context, ProfileActivity::class.java)
            startActivity(mainIntent)

        }
        logtoutimg.setOnClickListener{
            requireActivity().finish()
        }
       edit.setOnClickListener{
           updatee()
        }




//        return rootView
    }


}