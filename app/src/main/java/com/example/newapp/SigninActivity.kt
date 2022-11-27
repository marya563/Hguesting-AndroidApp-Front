package com.example.newapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.example.newapp.API.RetrofitInstance
import com.example.newapp.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject


import com.google.gson.Gson

const val PREF_NAME = "LOGIN_PREF_NONE"
const val EMAIL = "EMAIL"
const val PASSWORD = "PASSWORD"
const val IS_REMEMBRED = "IS_REMEMBRED"
const val ID = "_ID"

class SigninActivity : AppCompatActivity(){
    lateinit var textinputedittext: TextInputEditText
    lateinit var Email: TextInputLayout
    lateinit var cbRememberMe: CheckBox
    lateinit var textinputeditpassword: TextInputEditText
    lateinit var password: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            val actionBar = supportActionBar
        val signin = findViewById<Button>(R.id.signin)

        textinputedittext = findViewById(R.id.textinputedittext)
        Email = findViewById(R.id.Email )
        textinputeditpassword = findViewById(R.id. textinputeditpassword)
        password = findViewById(R.id.password)
        cbRememberMe = findViewById(R.id.cbRememberMe)



       signin.setOnClickListener {
            doLogin()
        }

        }
    private fun navigate() {
        val intentHome = Intent(this,MainActivity::class.java)
        startActivity(intentHome)
    }
        private fun doLogin() {
            if (validate()) {
                val apiInterface = RetrofitInstance.api(this)
                val user = User()
                user.email = textinputedittext.text.toString()
                user.password = textinputeditpassword.text.toString()

                apiInterface.login(user).enqueue(object : Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {

                        val status = response.code()
                        Log.w("Code", status.toString())


                        if (status == 200) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            print(response.body())
// user._id = jsonObject.getString("_id").toString()
//   Log.w("userID", user._id!!.toString())
/*     mSharedPref.edit().apply{
         putString(ID, user._id!!.toString())
     }.apply() */
                            val toast =
                                Toast.makeText(
                                    applicationContext,
                                    "Connected Successfully!",
                                    Toast.LENGTH_SHORT
                                )
                            toast.show()
                            navigate()
                        } else {
                            val toast =
                                Toast.makeText(
                                    applicationContext,
                                    "Check your parameters!",
                                    Toast.LENGTH_SHORT
                                )
                            toast.show()
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {

                        val toast =
                            Toast.makeText(applicationContext, "Connection Error !", Toast.LENGTH_SHORT)
                        toast.show()
                    }

                })

            }
        }
        private fun validate(): Boolean {
           Email.error = null
           password.error = null

            if (textinputedittext.text!!.isEmpty()) {
               Email.error = getString(R.string.emptyField)
                return false
            }

            if (textinputeditpassword.text!!.isEmpty()) {
               password.error = getString(R.string.emptyField)
                return false
            }

            return true



    }
        }