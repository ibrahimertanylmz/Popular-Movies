package com.wellbees.popularmovies.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.model.MovieResponse
import com.wellbees.popularmovies.model.Person
import com.wellbees.popularmovies.model.PersonResponse
import com.wellbees.popularmovies.service.PersonApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext

class PersonViewModel(private val personApiService: PersonApiService) : ViewModel() {

    var searchPeopleLiveData = MutableLiveData<PersonResponse>()
    val personLoadingStateLiveData = MutableLiveData<String>()

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

    fun getPeopleFromResponse(personResponse: PersonResponse): ArrayList<Person> {
        val personList = ArrayList<Person>()
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

}