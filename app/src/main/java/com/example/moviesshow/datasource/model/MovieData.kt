package com.example.moviesshow.datasource.model

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val movies: List<Movies>,
    @SerializedName("totalResults")
    val totalResults: String
) {
    data class Movies(
        @SerializedName("imdbID")
        val imdbID: String,
        @SerializedName("Poster")
        val posterUrl: String,
        @SerializedName("Title")
        val title: String,
        @SerializedName("Type")
        val type: String,
        @SerializedName("Year")
        val year: String
    )
}