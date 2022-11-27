package com.example.newapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.content.SharedPreferences
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.newapp.API.RetrofitInstance
import com.example.newapp.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

        lateinit var mSharedPref: SharedPreferences
        lateinit var editfirstname: TextInputEditText
        lateinit var Firstname: TextInputLayout

        lateinit var editlastname: TextInputEditText
        lateinit var lastname: TextInputLayout

        lateinit var emailedit: TextInputEditText
        lateinit var Email: TextInputLayout

        lateinit var textinputeditpassword: TextInputEditText
        lateinit var password: TextInputLayout

        lateinit var editpassword2: TextInputEditText
        lateinit var password2: TextInputLayout

       lateinit var editrole: TextInputEditText
       lateinit var role: TextInputLayout

      // lateinit var toolbar : Toolbar

  //role tetbadel value statiquement

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_register)
            mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
           // toolbar = findViewById(R.id.lefticon)


            editfirstname = findViewById(R.id.editfirstname)
            Firstname = findViewById(R.id.Firstname)

            emailedit = findViewById(R.id.emailedit)
            Email = findViewById(R.id.Email)

            textinputeditpassword = findViewById(R.id.textinputeditpassword)
            password = findViewById(R.id.password)

            editpassword2 = findViewById(R.id.editpassword2)
            password2 = findViewById(R.id.password2)

            editlastname = findViewById(R.id.editlastname)
            lastname = findViewById(R.id.lastname)

            editrole = findViewById(R.id.editrole)
            role = findViewById(R.id.role)

    /*toolbar.setNavigationOnClickListener{
    val intent = Intent(this, MainActivity::class.java)


    startActivity(intent)
        }*/
            val signup = findViewById<Button>(R.id.signup)

            signup.setOnClickListener {
                doRegister()
            }

        }

            private fun doRegister() {
                if (validate()) {
                    val apiInterface = RetrofitInstance.api(this)
                    val user = User()
                    user.lastName = editlastname.text.toString()
                    user.firstname = editfirstname.text.toString()
                    user.email = emailedit.text.toString()
                    user.password = textinputeditpassword.text.toString()
                     user.role = editrole.text.toString()


                    apiInterface.register(user).enqueue(object : Callback<ResponseBody> {

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            val stringResponse = response.body()?.string()
                            val jresponse = JSONObject(stringResponse!!)
                            val jresponse = JSONObject(Gson().toJson(response.body()))
                            val userid = jresponse.getString("_id").toString()

                            mSharedPref.edit().apply{
                                putString(ID, userid)
                            }.apply()

                            val status = response.code()
                            Log.w("Status Code", status.toString())


                            if (status ==500) {
                                val toast =
                                    Toast.makeText(
                                        applicationContext,
                                        "Thank you for creating an account!",
                                        Toast.LENGTH_SHORT
                                    )
                                toast.show()
                                navigate()
                            } else if (status == 400) {
                                val toast =
                                    Toast.makeText(
                                        applicationContext,
                                        "email already in use!",
                                        Toast.LENGTH_SHORT
                                    )

                                toast.show()

                            }
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {


                            val toast =
                                Toast.makeText(
                                    applicationContext,
                                    "Connexion error!",
                                    Toast.LENGTH_SHORT
                                )
                            toast.show()
                        }

                    })


                }
            }
        private fun navigate(){
            val mainIntent = Intent(this, ProfilPicUpload::class.java)
            startActivity(mainIntent)
        }

        private fun validate(): Boolean {
            Email.error = null
            password.error = null
            Firstname.error = null
            lastname.error = null
            password2.error = null
            role.error=null

            if (emailedit.text!!.isNullOrEmpty()) {
                emailedit.error = getString(R.string.emptyField)
                return false
            }

            if (textinputeditpassword.text!!.isNullOrEmpty()) {
                textinputeditpassword.error = getString(R.string.emptyField)
                return false
            }

            if ( editfirstname.text!!.isNullOrEmpty()) {
                editfirstname.error = getString(R.string.emptyField)
                return false
            }
            if ( editlastname.text!!.isNullOrEmpty()) {
                editlastname.error = getString(R.string.emptyField)
                return false
            }
            if ( editpassword2.text!!.isNullOrEmpty()) {
                editpassword2.error = getString(R.string.emptyField)
                return false
            }
            if ( editrole.text!!.isNullOrEmpty()) {
                editrole.error = getString(R.string.emptyField)
                return false
            }

            /*if (txtPassword.text!!.equals(txtConfirmPassword.text!!) == false) {
                txtLayoutConfirmPassword.error = getString(R.string.passwordsDontMatch)
                return false
            }*/
            return true
        }


    }


