package com.example.hotelbooking.ui.backend

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hotelbooking.R
import com.example.hotelbooking.api.RestApiService
import com.example.hotelbooking.api.RetrofitInstance
import com.example.hotelbooking.models.Hotel
import com.example.hotelbooking.models.ServiceRoom
import com.example.hotelbooking.ui.HomeActivity
import com.example.hotelbooking.ui.HotelMainActivity
import kotlinx.android.synthetic.main.activity_hotel_management.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream

class ServiceRoomManagmentActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var description: EditText
    private lateinit var price: EditText
    private lateinit var adress: EditText
    private lateinit var image: EditText
    private lateinit var filepath : Uri
    private lateinit var path : String
    private lateinit var lastimg : String
    private lateinit var btnadd : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_room_managment)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("ServiceRoom Management")


        btnchoose.setOnClickListener{
            startFileChooser()
        }

        val btnadd = findViewById<Button>(R.id.btnadd)

        btnadd.setOnClickListener {

            val name = findViewById<EditText>(R.id.name)
            val description = findViewById<EditText>(R.id.description)
            val price = findViewById<EditText>(R.id.price)
            val type = findViewById<EditText>(R.id.type)

            val nameServiceRoom = name.text.toString().trim()
            val descriptionServiceRoom = description.text.toString().trim()
            val typeServiceRoom = type.text.toString().trim()
            val priceServiceRoom = price.text.toString().trim()

            if (nameServiceRoom.isEmpty()){
                name.error="Hotel Name required"
                return@setOnClickListener
            }else if (descriptionServiceRoom.isEmpty()){
                description.error="Description required"
                return@setOnClickListener
            }else if (typeServiceRoom.isEmpty()){
                adress.error="Type required"
                return@setOnClickListener
            }else if (priceServiceRoom.isEmpty()){
                price.error="Price required"
                return@setOnClickListener
            }else{
                Log.d("testttttttttttttt",nameServiceRoom)
                addRoomService( nameServiceRoom, typeServiceRoom, descriptionServiceRoom, priceServiceRoom.toInt() )
                startActivity(
                    Intent(this, HotelMainActivity::class.java)
                )
            }


        }
    }

    @SuppressLint("Range")
    private fun addRoomService(name: String, typeService: String, description: String, price: Int) {

        val fileName = contentResolver.query(filepath, null, null, null, null).use {
            if (it != null) {
                it.moveToFirst()
            }
            it?.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        val inputStream = contentResolver.openInputStream(filepath)
        val bytes = ByteArrayOutputStream().use {
            if (inputStream != null) {
                inputStream.copyTo(it)
            }
            it.toByteArray()
        }
        val type = contentResolver.getType(filepath)
        val requestFile = RequestBody.create(type?.toMediaTypeOrNull(), bytes)
        val Retrofit = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val id : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "")
        val nameRoomService : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val descriptionRoomService : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val typeRoomService : RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), typeService)
        val image = MultipartBody.Part.createFormData("image", fileName, requestFile)

        Retrofit.addRoomService(id,nameRoomService,typeRoomService,descriptionRoomService,price,image).enqueue(object : Callback<ServiceRoom> {
            override fun onResponse(call: Call<ServiceRoom>, response: retrofit2.Response<ServiceRoom>) {
                Toast.makeText(this@ServiceRoomManagmentActivity,"Successfully Added", Toast.LENGTH_SHORT).show()


            }

            override fun onFailure(call: Call<ServiceRoom>, t: Throwable) {
                Toast.makeText(this@ServiceRoomManagmentActivity,"Failed to add Item", Toast.LENGTH_SHORT).show()
            }




        })

    }


    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"choose Picture"),111)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==111 && resultCode== Activity.RESULT_OK && data != null) {

            filepath = data.data!!
            Log.d("TAG", filepath.toString())
            path = filepath.toString().substring(filepath.toString().lastIndexOf("%")+3)
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageView.setImageBitmap(bitmap)
            Log.d("TAG", bitmap.toString());
            Log.d("TAG",getRealPathFromURIAPI19(baseContext,filepath).toString())

        }
    }
    @SuppressLint("NewApi")
    fun getRealPathFromURIAPI19(context: Context, uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                var cursor: Cursor? = null
                try {
                    cursor = context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null)
                    cursor!!.moveToNext()
                    val fileName = cursor.getString(0)
                    val path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                    if (!TextUtils.isEmpty(path)) {
                        return path
                    }
                } finally {
                    cursor?.close()
                }
                val id = DocumentsContract.getDocumentId(uri)
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:".toRegex(), "")
                }
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), java.lang.Long.valueOf(id))

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)

        return null
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                              selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority

    }
}