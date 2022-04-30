package com.wellbees.popularmovies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wellbees.popularmovies.service.PersonApiService

class PersonViewModelFactory (private val personApiService: PersonApiService): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((PersonViewModel::class.java))){
            return PersonViewModel(personApiService) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}