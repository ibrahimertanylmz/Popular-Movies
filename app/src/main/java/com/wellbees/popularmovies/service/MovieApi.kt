package com.wellbees.popularmovies.service

import com.wellbees.popularmovies.model.MovieDetailsResponse
import com.wellbees.popularmovies.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    //https://api.themoviedb.org/3/search/movie?api_key=39c5465cd4d393531f1e739433a8e360&language=en-US&query=batman&page=1&include_adult=false

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

}