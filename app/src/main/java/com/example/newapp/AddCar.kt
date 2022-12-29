 package com.example.newapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.newapp.API.UserApi
import com.example.newapp.API.Order
import com.example.newapp.API.RetrofitInstance
import com.example.newapp.models.Car
import com.example.newapp.models.Rclient
import com.example.newapp.models.User
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener
//import com.sendbird.android.GroupChannel
//import com.sendbird.android.GroupChannelParams
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.DatagramChannel.open
import java.nio.channels.Selector.open
import kotlin.collections.HashMap


class AddCar : AppCompatActivity(), PaymentResultWithDataListener {
    lateinit var retrofit: Retrofit
    lateinit var retroInterface: UserApi
    lateinit var editdays: TextInputEditText
    lateinit var days: TextInputLayout

    //lateinit var success: TextView
    //lateinit var failed : TextView
    //lateinit var card : CardView
    private lateinit var dateTv: TextView
    private lateinit var dateTv2: TextView
    private lateinit var checkin: Button
    private lateinit var checkout: Button
    private lateinit var Continue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        val gson = GsonBuilder().setLenient()
        Checkout.preload(applicationContext)

        Continue = findViewById(R.id.Paybt)
        dateTv = findViewById(R.id.dateTv)
        dateTv2 = findViewById(R.id.dateTv2)
        checkin = findViewById(R.id.checkin)
        checkout = findViewById(R.id.checkout)
        val myCalendar = Calendar.getInstance()
        val myCalendar2 = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        val datePicker2 = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar2.set(Calendar.YEAR, year)
            myCalendar2.set(Calendar.MONTH, month)
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable2(myCalendar2)
        }
        retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3001")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retroInterface = retrofit.create(UserApi::class.java)

        checkin.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        checkout.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker2,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        Continue.setOnClickListener {
            val priceEdit: EditText = findViewById(R.id.priceEdit)
            val price = priceEdit.text.toString()
            if (price.isEmpty()) {
                return@setOnClickListener
            }

            getOrderId(price)
        }


    }

        private fun getOrderId(price: String) {
            val map = HashMap<String, String>()
            map["price"] = price
            retroInterface
                .getOrderId(map)
                .enqueue(object : Callback<Order> {
                    override fun onResponse(call: Call<Order>, response: Response<Order>) {
                        if (response.body() != null)
                            initiatePayment(price, response.body()!!)
                    }

                    private fun initiatePayment(price: String, order: Order) {
                        val checkout = Checkout()
                        checkout.setKeyID(order.getKeyId())

                        checkout.setImage(R.drawable.black___beige_luxury_aesthetic_minimal_font_logo__3_)

                        val paymentOptions = JSONObject()
                        paymentOptions.put("name", "Hotel Guesting")
                        paymentOptions.put("price", price)
                        paymentOptions.put("order_id", order.getOrderId())
                        paymentOptions.put("currency", "INR")
                        paymentOptions.put("description", "Refrence no:#1234")
                        checkout.open(this@AddCar, paymentOptions)

                    }
                    override fun onFailure(call: Call<Order>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        dateTv.setText(sdf.format(myCalendar.time))
    }
    private fun updateLable2(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        dateTv2.setText(sdf.format(myCalendar.time))
    }


  /*  private fun addCarr(userId: String, pickupdate : String, dropoffdate : String, numberofdays : String) {
        val apiInterface = RetrofitInstance.api(this)
        val carClient = Car()

        Rclient.pickupdate = pickupdate
        Rclient.userId = userId
        Rclient.dropoffdate = dropoffdate

        apiInterface.addCarr(carClient).enqueue(object : Callback<Car> {

            override fun onResponse(call: Call<Car>, response: Response<Car>) {
                Log.w("rental added ", "rental added")
            }

            override fun onFailure(call: Call<Car>, t: Throwable) {

            }

        })}
*/
    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        val map = HashMap<String, String>()
        map["order_id"] = p1!!.orderId
        map["pay_id"] = p1.paymentId
        map["signature"] = p1.signature
        retroInterface.updateTransactionStatus(map)
            .enqueue(object  : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.body().equals("success"))
                        Toast.makeText(this@AddCar,"Payment successful",Toast.LENGTH_LONG).show()
                }
            })
          }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this,"Payment failed", Toast.LENGTH_LONG).show()

    }
}





