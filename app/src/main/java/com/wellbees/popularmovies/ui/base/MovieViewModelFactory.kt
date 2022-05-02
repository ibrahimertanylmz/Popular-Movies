package com.wellbees.popularmovies.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbees.popularmovies.service.MovieApiService
import com.wellbees.popularmovies.ui.viewmodel.MovieViewModel

class MovieViewModelFactory(private val movieApiService: MovieApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((MovieViewModel::class.java))) {
            return MovieViewModel(movieApiService) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}