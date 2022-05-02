package com.wellbees.popularmovies.service

import com.wellbees.popularmovies.model.CastResponse
import com.wellbees.popularmovies.model.GenreResponse
import com.wellbees.popularmovies.model.MovieDetailsResponse
import com.wellbees.popularmovies.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("search/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = "39c5465cd4d393531f1e739433a8e360",
        @Query("page") page: Int,
        @Query("query") query: String
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "39c5465cd4d393531f1e739433a8e360",
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "39c5465cd4d393531f1e739433a8e360",
    ): CastResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = "39c5465cd4d393531f1e739433a8e360",
    ): GenreResponse

}