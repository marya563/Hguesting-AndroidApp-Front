package com.example.newapp

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newapp.API.RetrofitInstance
import com.example.newapp.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class updateProfileFragment : Fragment() {
    lateinit var firstnameUpdate: TextInputEditText
    lateinit var firstnameLayoutUpdate: TextInputLayout

    lateinit var lastnameUpdate: TextInputEditText
    lateinit var lastnameLayoutUpdate: TextInputLayout


    lateinit var confirmePasswordUpdate: TextInputEditText
    lateinit var confirmePasswordLayoutUpdate: TextInputLayout
    lateinit var passwordUpdate: TextInputEditText
    lateinit var passwordLayoutUpdate: TextInputLayout
    lateinit var imageUpdate: ImageView
    lateinit var updatebutton: Button
    lateinit var mSharedPref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_update_profile, container, false)
        firstnameUpdate = rootView.findViewById(R.id.firstnameUpdateText)
        firstnameLayoutUpdate = rootView.findViewById(R.id.firstnameUpdate)
        lastnameUpdate = rootView.findViewById(R.id.lastnameUpdateText)
        lastnameLayoutUpdate = rootView.findViewById(R.id.lastnameUpdate)
        confirmePasswordUpdate = rootView.findViewById(R.id.confirmePasswordUapadteText)
        confirmePasswordLayoutUpdate = rootView.findViewById(R.id.confirmePasswordUapadte)
        passwordUpdate = rootView.findViewById(R.id.passwordUapadteText)
        passwordLayoutUpdate = rootView.findViewById(R.id.passwordUapadte)
        updatebutton = rootView.findViewById(R.id.button3)
        imageUpdate = rootView.findViewById(R.id.imageUpdate)
        mSharedPref =
            requireActivity().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE);
        val nomStr: String = mSharedPref.getString("firstname", null).toString()
        val prenomStr: String = mSharedPref.getString("lastname", null).toString()
        val profilePic: String = mSharedPref.getString("profilePic", null).toString()

        Log.w("TAG", "profilePic: " + profilePic )
        val idUser: String = mSharedPref.getString("_id", null).toString()
        //nomUpdate.text = nomStr.
        //prenomUpdate.text = prenomStr


        // imageUpdate.loadurl("http://127.0.0.1:3001/uploads/profilePic-1669560624504.jpg")
        Glide.with(this).load(profilePic).placeholder(R.drawable.unselected).into(imageUpdate)
        updatebutton.setOnClickListener{

          /*  val url = "RetrofitInstance.BASE_URL + profilePic"
            Glide.with(this@updateProfileFragment).load(url)
                .placeholder(R.drawable.unselected).into(imageUpdate) */

            val nom = firstnameUpdate.text.toString().trim()
            val prenom = lastnameUpdate.text.toString().trim()
            val password = passwordUpdate.text.toString().trim()
            val confirmPassword = confirmePasswordUpdate.text.toString().trim()

            if (validate()) {
                //val apiInterface = UserApi.create()
                val apiInterface = RetrofitInstance.api(context)
                //progBar.visibility = View.VISIBLE

                var user = User()
                user.firstname = nom
                user.lastname = prenom
                user.password = password
                apiInterface.updateUserrById(idUser).enqueue(object :
                    Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        @SuppressLint("SuspiciousIndentation")

                        val status = response.code()
                        Log.w("Code", status.toString())

                        if (status == 200) {

                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            user._id = jsonObject.getString("_id").toString()
                            Log.w("user", user.toString())
                            mSharedPref.edit().apply {
                                putString(ID, user._id!!.toString())
                            }.apply()

                        } else {
                            val toast =
                                Toast.makeText(context, "errrorr !", Toast.LENGTH_SHORT)
                            toast.show()
                            toast.show()
                        }
                    }
                    // val user = response.body(

                    override fun onFailure(call: Call<User>, t: Throwable) {

                        Toast.makeText(context, "Connexion error!", Toast.LENGTH_SHORT).show()

                    }

                })

            }


        }
        return rootView
    }
    private fun validate(): Boolean {
        lastnameLayoutUpdate.error = null
        firstnameLayoutUpdate.error = null
        confirmePasswordUpdate.error = null
        passwordLayoutUpdate.error = null

        if (firstnameUpdate.text!!.isEmpty()) {
            firstnameLayoutUpdate.error = getString(R.string.emptyField)
            return false
        }
        if (lastnameUpdate.text!!.isEmpty()) {
            lastnameLayoutUpdate.error = getString(R.string.emptyField)
            return false
        }
        if (confirmePasswordUpdate.text!!.isEmpty()) {
            confirmePasswordLayoutUpdate.error = getString(R.string.emptyField)
            return false
        }

        if (passwordUpdate.text!!.isEmpty()) {
            passwordLayoutUpdate.error = getString(R.string.emptyField)
            return false
        }
        if (passwordUpdate.text!!.length < 8) {
            passwordLayoutUpdate.setError("Password Length must be more than " + 6 + "characters")
            return false
        }

        // Checking if repeat password is same
        if (!passwordUpdate.text.toString().equals(confirmePasswordUpdate.text.toString())) {
            confirmePasswordLayoutUpdate.setError("Password does not match")
            return false
        }


        return true
    }



}