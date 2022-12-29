package com.example.newapp.models

import okhttp3.MultipartBody

class Car (var _carid: String ? = null,
           var cartype: String ? = null,
           var carbrand: String ? = null,
           var carprice: String ? = null,
           var carPic: MultipartBody.Part ? = null,
           var carengine: String ? = null,)
