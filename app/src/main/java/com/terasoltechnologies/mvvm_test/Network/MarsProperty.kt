package com.terasoltechnologies.mvvm_test.Network

import com.squareup.moshi.Json

data class MarsProperty(
    val id: String,
    val img_src: String, //@Json(name = "img_src")
    val type: String,
    val price: Double
)