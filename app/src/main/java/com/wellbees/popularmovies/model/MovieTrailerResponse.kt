package com.wellbees.popularmovies.model


import com.google.gson.annotations.SerializedName

data class MovieTrailerResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val trailers: List<Trailer>
)