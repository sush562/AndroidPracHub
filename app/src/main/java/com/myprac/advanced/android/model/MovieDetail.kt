package com.myprac.advanced.android.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(@SerializedName("adult") val isAdult: Boolean = false,
                       @SerializedName("backdrop_path") val backdropPath: String = "",
                       @SerializedName("budget") val budget: Long = 0,
                       @SerializedName("genres") val genreList: ArrayList<MovieDetailGenre> = ArrayList(),
                       @SerializedName("homepage") val homePageLink: String = "",
                       @SerializedName("id") val id: Long = 0,
                       @SerializedName("imdb_id") val imdbId: String = "",
                       @SerializedName("original_language") val originalLanguageCode: String = "",
                       @SerializedName("original_title") val originalTitle: String = "",
                       @SerializedName("overview") var overview: String = "Fetching Overview...",
                       @SerializedName("popularity") val popularity: Double = 0.0,
                       @SerializedName("poster_path") val posterPath: String = "",
                       @SerializedName("production_companies") val prodCompaniesList: ArrayList<MovieDetailProdCompanies> = ArrayList(),
                       @SerializedName("release_date") val releaseDate: String = "",
                       @SerializedName("revenue") val revenue: Long = 0,
                       @SerializedName("runtime") val runtime: Int = 0,
                       @SerializedName("spoken_languages") val spokenLangList: ArrayList<MovieDetailSpokenLang> = ArrayList(),
                       @SerializedName("status") val status: String = "",
                       @SerializedName("tagline") val tagline: String = "",
                       @SerializedName("title") val title: String = "",
                       @SerializedName("video") val video: Boolean = false,
                       @SerializedName("vote_average") val voteAverage: Float = 0.0f,
                       @SerializedName("vote_count") val voteCount: Long = 0)

data class MovieDetailGenre(@SerializedName("id") val id: Int,
                            @SerializedName("name") val name: String)

data class MovieDetailProdCompanies(@SerializedName("id") val id: Long,
                                    @SerializedName("logo_path") val logoPath: String,
                                    @SerializedName("name") val name: String)

data class MovieDetailSpokenLang(@SerializedName("name") val name: String)