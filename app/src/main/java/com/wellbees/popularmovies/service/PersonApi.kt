package com.wellbees.popularmovies.service

import com.wellbees.popularmovies.model.PersonDetailResponse
import com.wellbees.popularmovies.model.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApi {
    //https://api.themoviedb.org/3/search/person?api_key=39c5465cd4d393531f1e739433a8e360&language=en-US&query=tarantino&page=1&include_adult=false

    @GET("search/person")
    suspend fun getPeople(
        @Query("api_key") apiKey: String = "39c5465cd4d393531f1e739433a8e360",
        @Query("page") page: Int,
        @Query("query") query: String
    ): PersonResponse

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String = "39c5465cd4d393531f1e739433a8e360"
    ): PersonDetailResponse
}