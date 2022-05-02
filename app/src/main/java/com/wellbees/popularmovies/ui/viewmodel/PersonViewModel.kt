package com.wellbees.popularmovies.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbees.popularmovies.model.*
import com.wellbees.popularmovies.service.PersonApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext

class PersonViewModel(private val personApiService: PersonApiService) : ViewModel() {

    var searchPeopleLiveData = MutableLiveData<PersonResponse>()
    var personDetailsLiveData = MutableLiveData<PersonDetailResponse>()
    val personLoadingStateLiveData = MutableLiveData<String>()
    val personDetailLoadingStateLiveData = MutableLiveData<String>()
    private var personList = ArrayList<Person>()

    fun onSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.length > 2) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        withContext(Dispatchers.Main) {
                            personLoadingStateLiveData.value = "LOADING"
                        }
                        val movies = personApiService.getPeople(1, query)
                        searchPeopleLiveData.postValue(movies)
                        personLoadingStateLiveData.postValue("LOADED")
                    } catch (e: Exception) {
                        personLoadingStateLiveData.postValue(e.message.toString())
                        Log.d("exception", e.message.toString())
                    }
                }
            }
        }
    }

    fun getPersonDetailsById(personId: Int) {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.Main) {
                        personDetailLoadingStateLiveData.value = "LOADING"
                    }
                    val personDetails = personApiService.getPersonDetails(personId)
                    personDetailsLiveData.postValue(personDetails)
                    personDetailLoadingStateLiveData.postValue("LOADED")
                } catch (e: Exception) {
                    personDetailLoadingStateLiveData.postValue(e.message.toString())
                    Log.d("exception", e.message.toString())
                }
            }
        }
    }

    fun getPeopleFromResponse(personResponse: PersonResponse): ArrayList<Person> {
        personList.clear()
        personResponse.results.forEach {
            val id = it.id
            val name = it.name
            var profilePath = ""
            if (it.profilePath != null) {
                profilePath = it.profilePath
            }
            val person = Person(id, name)
            person.profilePath = profilePath
            personList.add(person)
        }
        return personList
    }

    fun getPersonId(position: Int): Int {
        return personList[position].id
    }

}