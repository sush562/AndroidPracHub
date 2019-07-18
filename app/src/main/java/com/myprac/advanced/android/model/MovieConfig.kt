package com.myprac.advanced.android.model

import com.google.gson.annotations.SerializedName

data class MovieConfigUrl(@SerializedName("base_url") val baseUrl: String,
                       @SerializedName("secure_base_url") val secureBaseUrl: String)

data class MovieConfig(@SerializedName("images") val imageConfig: MovieConfigUrl)