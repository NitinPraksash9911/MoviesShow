package com.example.moviesshow.datasource.repository

import com.example.moviesshow.arch.Resource
import com.example.moviesshow.datasource.model.MovieData
import com.example.moviesshow.datasource.model.MovieDetailData

interface IMovieRepository {

    suspend fun fetchMovies(query:String,pageNo:Int): Resource<MovieData>

    suspend fun fetchMovieDetail(imdbId:String): Resource<MovieDetailData>

}