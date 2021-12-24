package com.example.moviesshow.datasource.apiservice

import com.example.moviesshow.BuildConfig
import com.example.moviesshow.datasource.model.MovieData
import com.example.moviesshow.datasource.model.MovieDetailData
import com.example.moviesshow.network.utils.Cacheable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @Cacheable
    @GET("?apiKey=${BuildConfig.API_KEY}")
    suspend fun fetchMovieData(@Query("s") query: String, @Query("page") pageNo: Int):
            Response<MovieData>

    @GET("?apiKey=${BuildConfig.API_KEY}")
    suspend fun fetchMovieDetail(@Query("i") imdbID: String):
            Response<MovieDetailData>
}