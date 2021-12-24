package com.example.moviesshow.datasource.repository

import com.example.moviesshow.arch.Resource
import com.example.moviesshow.datasource.apiservice.MovieApiService
import com.example.moviesshow.datasource.model.MovieData
import com.example.moviesshow.datasource.model.MovieDetailData
import com.example.moviesshow.network.utils.getResult
import javax.inject.Inject

class MovieRepository @Inject constructor(private var movieApiService: MovieApiService) : IMovieRepository {

    override suspend fun fetchMovies(query: String, pageNo: Int): Resource<MovieData> {
        return getResult {
            movieApiService.fetchMovieData(query, pageNo)
        }
    }

    override suspend fun fetchMovieDetail(imdbId: String): Resource<MovieDetailData> {
        return getResult {
            movieApiService.fetchMovieDetail(imdbId)
        }
    }

}