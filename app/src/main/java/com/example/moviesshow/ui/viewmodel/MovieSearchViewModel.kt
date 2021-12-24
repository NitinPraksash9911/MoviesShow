package com.example.moviesshow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesshow.arch.Resource
import com.example.moviesshow.arch.ViewState
import com.example.moviesshow.datasource.model.MovieData
import com.example.moviesshow.datasource.model.MovieDetailData
import com.example.moviesshow.datasource.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val repository: IMovieRepository,
) : ViewModel() {

    fun searchMovies(query: String, pageNo: Int = 1): StateFlow<ViewState<MovieData>> {
        return flow {

            when (val result = repository.fetchMovies(query, pageNo)) {
                is Resource.Success -> {

                    when (result.value.response) {
                        "False" -> {
                            emit(ViewState.Empty)
                        }
                        else -> {
                            emit(ViewState.Success(result.value))

                        }
                    }

                }
                is Resource.Error -> {
                    emit(ViewState.Error(result.msg))
                }
            }

        }.stateIn(
            initialValue = ViewState.Loading,
            scope = viewModelScope,
            // it tell the flow active only for 5 second if there is no collector
            started = SharingStarted.WhileSubscribed(5000)
        )

    }

    fun fetchMovieDetail(movieId: String): StateFlow<ViewState<MovieDetailData>> {
        return flow {

            when (val result = repository.fetchMovieDetail(movieId)) {
                is Resource.Success -> {

                    when (result.value.response) {
                        "False" -> {
                            emit(ViewState.Empty)
                        }
                        else -> {
                            emit(ViewState.Success(result.value))

                        }
                    }

                }
                is Resource.Error -> {
                    emit(ViewState.Error(result.msg))
                }
            }

        }.stateIn(
            initialValue = ViewState.Loading,
            scope = viewModelScope,
            // it tell the flow active only for 5 second if there is no collector
            started = SharingStarted.WhileSubscribed(5000)
        )

    }

}