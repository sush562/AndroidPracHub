package com.myprac.advanced.android.model

import com.google.gson.annotations.SerializedName

data class MovieConfigUrl(@SerializedName("base_url") val baseUrl: String,
                       @SerializedName("secure_base_url") val secureBaseUrl: String)

data class MovieConfig(@SerializedName("images") val imageConfig: MovieConfigUrl)

data class MovieList(@SerializedName("page") var page: Int,
                     @SerializedName("total_results") var totalResults: Int,
                     @SerializedName("total_pages") var totalPages: Int,
                     @SerializedName("results") var results: List<MovieResult>)

data class MovieResult(@SerializedName("vote_count") var voteCount: Int,
                       @SerializedName("id") var id: Long,
                       @SerializedName("video") var video: Boolean)