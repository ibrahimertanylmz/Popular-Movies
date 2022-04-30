package com.wellbees.popularmovies.service

import com.wellbees.popularmovies.model.MovieResponse
import com.wellbees.popularmovies.model.PersonResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PersonApiService {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PersonApi::class.java)

    suspend fun getPeople(page: Int, query: String): PersonResponse {
        return api.getData("39c5465cd4d393531f1e739433a8e360",page,query)
    }
}