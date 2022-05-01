package com.wellbees.popularmovies.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.model.MovieDetailsResponse
import com.wellbees.popularmovies.model.MovieResponse
import com.wellbees.popularmovies.model.Person
import com.wellbees.popularmovies.service.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieViewModel(private val movieApiService: MovieApiService) : ViewModel() {
    var searchMoviesLiveData = MutableLiveData<MovieResponse>()
    var movieDetailsLiveData = MutableLiveData<MovieDetailsResponse>()
    val movieLoadingStateLiveData = MutableLiveData<String>()
    val movieDetailLoadingStateLiveData = MutableLiveData<String>()
    private var movieList = ArrayList<Movie>()

    fun onSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.length > 2) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        withContext(Dispatchers.Main) {
                            movieLoadingStateLiveData.value = "LOADING"
                        }
                        val movies = movieApiService.getMovies(1, query)
                        searchMoviesLiveData.postValue(movies)
                        movieLoadingStateLiveData.postValue("LOADED")
                    } catch (e: Exception) {
                        movieLoadingStateLiveData.postValue(e.message.toString())
                        Log.d("error", e.message.toString())
                    }
                }
            }
        }
    }

    fun getDetailsByMovieId(movieId: Int) {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.Main) {
                        movieDetailLoadingStateLiveData.value = "LOADING"
                    }
                    val movieDetails = movieApiService.getDetailsOfMovie(movieId)
                    movieDetailsLiveData.postValue(movieDetails)
                    movieDetailLoadingStateLiveData.postValue("LOADED")
                } catch (e: Exception) {
                    movieDetailLoadingStateLiveData.postValue(e.message.toString())
                    Log.d("error", e.message.toString())
                }


            }
        }
    }

    fun getMoviesFromResponse(movieResponse: MovieResponse): ArrayList<Movie> {
        movieList.clear()
        movieResponse.results.forEach {
            val id = it.id
            val name = it.originalTitle
            var posterPath = ""
            var releaseDate = ""
            if (it.posterPath != null) {
                posterPath = it.posterPath
            }
            it.releaseDate?.let { rd -> releaseDate = rd }
            val movie = Movie(id, name)
            movie.posterPath = posterPath
            movie.releaseDate = releaseDate
            movieList.add(movie)
        }
        return movieList
    }

    fun getMovieId(position: Int): Int{
        return movieList[position].id
    }

}