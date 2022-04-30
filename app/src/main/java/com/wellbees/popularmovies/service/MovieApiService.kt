package com.wellbees.popularmovies.service

import com.wellbees.popularmovies.model.Movie
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://api.themoviedb.org/3/search/company?api_key=39c5465cd4d393531f1e739433a8e360&query=attackontitan&page=1
class MovieApiService {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieAPİ::class.java)

    suspend fun getDataService(page: Int, query: String): Movie{
        return api.getData("39c5465cd4d393531f1e739433a8e360",page,query)
    }
}