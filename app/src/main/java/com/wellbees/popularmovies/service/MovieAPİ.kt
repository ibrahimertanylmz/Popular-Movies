package com.wellbees.popularmovies.service

import com.wellbees.popularmovies.model.Movie
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPİ {
    //https://api.themoviedb.org/3/search/company?api_key=39c5465cd4d393531f1e739433a8e360&query=attackontitan&page=1
    //https://api.themoviedb.org/3/search/company?query=attackontitan&page=1&api_key=39c5465cd4d393531f1e739433a8e360

        @GET("search/company")
        suspend fun getData(
            @Query("api_key") apiKey: String = "39c5465cd4d393531f1e739433a8e360",
            @Query("page") page: Int,
            @Query("query") query: String
        ): Movie
}