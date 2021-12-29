package com.example.moviesshow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesshow.arch.Resource
import com.example.moviesshow.arch.ViewState
import com.example.moviesshow.datasource.model.MovieData
import com.example.moviesshow.datasource.model.MovieDetailData
import com.example.moviesshow.datasource.repository.IMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(
    private val repository: IMovieRepository,
) : ViewModel() {

//    private val _uiEvent = Channel<ViewState<MovieData>>()
//    val uiEvent = _uiEvent.receiveAsFlow()

//    private var query = MutableStateFlow<String>("").debounce(600)
//        .filter {
//            (it.isNotEmpty())
//        }
//
//    fun query(search: String) {
//
//        query
//    }

    fun searchMovies(query: String, pageNo: Int = 1): Flow<ViewState<MovieData>> {
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
            scope = viewModelScope,
            initialValue = ViewState.Loading,
            // it tell the flow active only for 5 second if there is no collector
            started = SharingStarted.WhileSubscribed(5000),

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