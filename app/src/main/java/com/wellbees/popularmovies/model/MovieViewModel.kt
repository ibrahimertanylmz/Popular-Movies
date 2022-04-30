package com.wellbees.popularmovies.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbees.popularmovies.service.MovieApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger


class MovieViewModel: ViewModel() {

    private var searchJob: Job? = null
    var searchMoviesLiveData = MutableLiveData<Movie>()
    val movieLoadingStateLiveData = MutableLiveData<String>()

    fun onSearchQuery(query: String)  {
        viewModelScope.launch {
            if (query.length > 2) {
                    //val liveData = MutableLiveData<List<Movie>>()
                    viewModelScope.launch(Dispatchers.IO) {

                        try {
                            //1
                            withContext(Dispatchers.Main) {
                                movieLoadingStateLiveData.value = "LOADING"
                            }

                            val movies = MovieApiService().getDataService(1,query)
                            searchMoviesLiveData.postValue(movies)

                            //2
                            movieLoadingStateLiveData.postValue("LOADED")

                            Log.d("basari","e.message.toString()")
                            //movieLoadingStateLiveData.postValue(MovieLoadingState.LOADED)
                        }catch (e: Exception){
                            movieLoadingStateLiveData.postValue(e.message.toString())
                            Log.d("hata",e.message.toString())
                        }


                    }
            }
        }
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