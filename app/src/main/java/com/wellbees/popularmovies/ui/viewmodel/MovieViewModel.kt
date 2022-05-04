package com.wellbees.popularmovies.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbees.popularmovies.model.*
import com.wellbees.popularmovies.service.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieViewModel(private val movieApiService: MovieApiService) : ViewModel() {
    var searchMoviesLiveData = MutableLiveData<MovieResponse>()
    var movieDetailsLiveData = MutableLiveData<MovieDetailsResponse>()
    var castLiveData = MutableLiveData<CastResponse>()
    var genreLiveData = MutableLiveData<GenreResponse>()
    var trailerLiveData = MutableLiveData<MovieTrailerResponse>()
    val movieLoadingStateLiveData = MutableLiveData<LoadState>()
    val movieDetailLoadingStateLiveData = MutableLiveData<LoadState>()
    val castLoadingStateLiveData = MutableLiveData<LoadState>()
    val genreLoadingStateLiveData = MutableLiveData<LoadState>()
    val trailerLoadingStateLiveData = MutableLiveData<LoadState>()
    private var movieList = ArrayList<Movie>()
    private val cast = ArrayList<Person>()
    private val genreList = ArrayList<Genre>()

    fun onSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.length > 2) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        withContext(Dispatchers.Main) {
                            movieLoadingStateLiveData.value = LoadState.Loading
                        }
                        val movies = movieApiService.getMovies(1, query)
                        searchMoviesLiveData.postValue(movies)
                        movieLoadingStateLiveData.postValue(LoadState.Loaded)
                    } catch (e: Exception) {
                        movieLoadingStateLiveData.postValue(LoadState.Error)
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
                        movieDetailLoadingStateLiveData.value = LoadState.Loading
                    }
                    val movieDetails = movieApiService.getDetailsOfMovie(movieId)
                    movieDetailsLiveData.postValue(movieDetails)
                    movieDetailLoadingStateLiveData.postValue(LoadState.Loaded)
                } catch (e: Exception) {
                    movieDetailLoadingStateLiveData.postValue(LoadState.Error)
                    Log.d("error", e.message.toString())
                }
            }
        }
    }

    fun getTrailerByMovieId(movieId: Int) {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.Main) {
                        trailerLoadingStateLiveData.value = LoadState.Loading
                    }
                    val movieVideos = movieApiService.getVideos(movieId)
                    trailerLiveData.postValue(movieVideos)
                    trailerLoadingStateLiveData.postValue(LoadState.Loaded)
                } catch (e: Exception) {
                    trailerLoadingStateLiveData.postValue(LoadState.Error)
                    Log.d("error", e.message.toString())
                }
            }
        }
    }

    fun getCastOfMovie(movieId: Int) {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.Main) {
                        castLoadingStateLiveData.value = LoadState.Loading
                    }
                    val cast = movieApiService.getCastOfMovie(movieId)
                    castLiveData.postValue(cast)
                    castLoadingStateLiveData.postValue(LoadState.Loaded)
                } catch (e: Exception) {
                    castLoadingStateLiveData.postValue(LoadState.Error)
                    Log.d("error", e.message.toString())
                    println()
                }
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.Main) {
                        genreLoadingStateLiveData.value = LoadState.Loading
                    }
                    genreList.clear()
                    val genreResponse = movieApiService.getGenres()
                    genreLiveData.postValue(genreResponse)
                    genreLoadingStateLiveData.postValue(LoadState.Loaded)
                } catch (e: Exception) {
                    genreLoadingStateLiveData.postValue(LoadState.Error)
                    Log.d("error", e.message.toString())
                    println()
                }
            }
        }
    }

    fun getFilteredGenreList(genreResponse: GenreResponse, genre: String): ArrayList<Genre> {
        val filteredList = ArrayList<Genre>()
        genreResponse.genres.forEach {
            if (it.name.contains(genre, ignoreCase = true)) {
                filteredList.add(it)
            }
        }
        return filteredList
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

    fun getMovieId(position: Int): Int {
        return movieList[position].id
    }

    fun getCastFromResponse(castResponse: CastResponse): ArrayList<Person> {
        cast.clear()
        castResponse.cast.forEach {
            val id = it.id
            val name = it.name
            var profilePath = ""

            if (it.profilePath != null) {
                profilePath = it.profilePath
            }

            val person = Person(id, name)
            person.profilePath = profilePath
            cast.add(person)
        }
        return cast
    }

    fun getPersonIdFromCast(position: Int): Int {
        return cast[position].id
    }

}