package com.example.moviesshow.network.utils

import com.example.moviesshow.arch.Resource
import com.example.moviesshow.arch.Resource.Error
import com.example.moviesshow.arch.Resource.Success
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Success(
                body
            )
        }

        Error(response.message())

    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> {
                Error(throwable.localizedMessage ?: "An IO exception happened ")
            }
            is HttpException -> {

                Error(throwable.message ?: "An Http exception happened ")
            }
            else -> {
                Error(throwable.message ?: "An Unknown exception happened ")
            }
        }
    }
}
