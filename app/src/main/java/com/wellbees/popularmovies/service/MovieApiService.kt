package com.wellbees.popularmovies.service

import com.wellbees.popularmovies.model.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieApiService {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApi::class.java)

    suspend fun getMovies(page: Int, query: String): MovieResponse{
        return api.getMovies("39c5465cd4d393531f1e739433a8e360",page,query)
    }

    suspend fun getDetailsOfMovie(movieId: Int): MovieDetailsResponse{
        return api.getDetails(movieId,"39c5465cd4d393531f1e739433a8e360")
    }

    suspend fun getCastOfMovie(movieId: Int): CastResponse{
        return api.getCast(movieId)
    }

    suspend fun getGenres(): GenreResponse {
        return api.getGenres()
    }

    suspend fun getVideos(movieId: Int): MovieTrailerResponse{
        return api.getVideos(movieId)
    }
}