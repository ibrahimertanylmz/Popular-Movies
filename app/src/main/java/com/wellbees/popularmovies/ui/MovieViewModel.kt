package com.wellbees.popularmovies.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.model.MovieResponse
import com.wellbees.popularmovies.service.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieViewModel(private val movieApiService: MovieApiService) : ViewModel() {
    private var searchJob: Job? = null
    var searchMoviesLiveData = MutableLiveData<MovieResponse>()
    val movieLoadingStateLiveData = MutableLiveData<String>()

    fun onSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.length > 2) {
                //val liveData = MutableLiveData<List<Movie>>()
                viewModelScope.launch(Dispatchers.IO) {

                    try {
                        //1
                        withContext(Dispatchers.Main) {
                            movieLoadingStateLiveData.value = "LOADING"
                        }

                        val movies = movieApiService.getMovies(1, query)
                        searchMoviesLiveData.postValue(movies)

                        //2
                        movieLoadingStateLiveData.postValue("LOADED")

                        Log.d("basari", "e.message.toString()")
                        //movieLoadingStateLiveData.postValue(MovieLoadingState.LOADED)
                    } catch (e: Exception) {
                        movieLoadingStateLiveData.postValue(e.message.toString())
                        Log.d("hata", e.message.toString())
                    }


                }
            }
        }
    }

    fun getMoviesFromResponse(movieResponse: MovieResponse): ArrayList<Movie> {
        val movieList = ArrayList<Movie>()
        movieResponse.results.forEach {
            val id = it.id
            val name = it.originalTitle
            var posterPath = ""
            var releaseDate = ""
            if (it.posterPath!= null){
                posterPath = it.posterPath
            }
            it.releaseDate?.let { rd-> releaseDate = rd }

            val movie = Movie(id, name)
            movie.posterPath = posterPath
            movie.releaseDate = releaseDate
            movieList.add(movie)
        }
        return movieList
    }

    //private val disposable = CompositeDisposable()


    /*private fun getDataFromAPI(movieName: String) {
        //country_load.value = true
        disposable.add(
            MovieApiService().getDataService(1,movieName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>() {
                    override fun onSuccess(t: Movie) {
                        //country_error.value = false
                        //country_load.value = false
                        //country_data.value = t
                    }
                    override fun onError(e: Throwable) {
                        //Log.d("MainViewModel", "onError -> $e")
                        //country_error.value = true
                        //country_load.value = false
                    }
                })
        )
    }*/

}