package com.example.moviesshow.di

import androidx.viewbinding.BuildConfig
import com.example.moviesshow.datasource.apiservice.MovieApiService
import com.example.moviesshow.datasource.repository.MovieRepository
import com.example.moviesshow.datasource.repository.IMovieRepository
import com.example.moviesshow.network.interceptor.LoggingInterceptor
import com.example.moviesshow.network.interceptor.NetworkCacheInterceptor
import com.example.moviesshow.network.interceptor.NetworkStatusInterceptor
import com.example.moviesshow.network.interceptor.OfflineCacheInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkStatusInterceptor: NetworkStatusInterceptor,
        networkCacheInterceptor: NetworkCacheInterceptor,
        offlineCacheInterceptor: OfflineCacheInterceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(networkStatusInterceptor)//1
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)//2
            }
            .addNetworkInterceptor(networkCacheInterceptor)
            .addInterceptor(offlineCacheInterceptor)
            .readTimeout(15, SECONDS)
            .writeTimeout(15, SECONDS)
            .connectTimeout(15, SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        factory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(factory)
        .baseUrl(com.example.moviesshow.BuildConfig.BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(loggingInterceptor)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideCatBreedService(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)

    @Singleton
    @Provides
    fun providesCatBreedRepository(breedService: MovieApiService): IMovieRepository = MovieRepository(breedService)

}