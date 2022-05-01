package com.wellbees.popularmovies.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbees.popularmovies.model.*
import com.wellbees.popularmovies.service.PersonApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
                //val liveData = MutableLiveData<List<Movie>>()
                viewModelScope.launch(Dispatchers.IO) {

                    try {
                        //1
                        withContext(Dispatchers.Main) {
                            personLoadingStateLiveData.value = "LOADING"
                        }

                        val movies = personApiService.getPeople(1, query)
                        searchPeopleLiveData.postValue(movies)

                        //2
                        personLoadingStateLiveData.postValue("LOADED")

                        Log.d("basari", "e.message.toString()")
                        //movieLoadingStateLiveData.postValue(MovieLoadingState.LOADED)
                    } catch (e: Exception) {
                        personLoadingStateLiveData.postValue(e.message.toString())
                        Log.d("hata", e.message.toString())
                    }


                }
            }
        }
    }

    fun getPersonDetailsById(personId: Int) {

        viewModelScope.launch {

            //val liveData = MutableLiveData<List<Movie>>()
            viewModelScope.launch(Dispatchers.IO) {

                try {
                    //1
                    withContext(Dispatchers.Main) {
                        personDetailLoadingStateLiveData.value = "LOADING"
                    }

                    val personDetails = personApiService.getPersonDetails(personId)
                    personDetailsLiveData.postValue(personDetails)

                    //2
                    personDetailLoadingStateLiveData.postValue("LOADED")

                    Log.d("basari", "e.message.toString()")
                    //movieLoadingStateLiveData.postValue(MovieLoadingState.LOADED)
                } catch (e: Exception) {
                    personDetailLoadingStateLiveData.postValue(e.message.toString())
                    Log.d("hata", e.message.toString())
                }


            }
        }

    }

    fun getPeopleFromResponse(personResponse: PersonResponse): ArrayList<Person> {
        personResponse.results.forEach {
            val id = it.id
            val name = it.name
            var profilePath = ""

            if(it.profilePath!= null){
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