package com.wellbees.popularmovies.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellbees.popularmovies.adapter.MovieAdapter
import com.wellbees.popularmovies.adapter.PersonAdapter
import com.wellbees.popularmovies.databinding.FragmentMovieItemsBinding
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.model.MovieResponse
import com.wellbees.popularmovies.model.Person
import com.wellbees.popularmovies.model.PersonResponse
import com.wellbees.popularmovies.service.MovieApiService
import com.wellbees.popularmovies.service.PersonApiService
import java.util.ArrayList

class MovieItemsFragment : Fragment() {

    private lateinit var binding: FragmentMovieItemsBinding
    lateinit var viewModelFactoryMovie: ViewModelProvider.Factory
    private lateinit var movieViewModel : MovieViewModel
    lateinit var viewModelFactoryPerson: ViewModelProvider.Factory
    private lateinit var personViewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieItemsBinding.inflate(inflater)

        initializeViewModels()

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.length > 2){
                        movieViewModel.onSearchQuery(binding.edtSearch.text.toString())
                        personViewModel.onSearchQuery(binding.edtSearch.text.toString())
                    }else{

                    }
                }
            }
        })


        movieViewModel.movieLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onMovieLoadingStateChanged(it)})

        personViewModel.personLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onPersonLoadingStateChanged(it)})


        return binding.root
    }

    private fun initializeViewModels() {
        val movieApiService = MovieApiService()
        viewModelFactoryMovie = MovieViewModelFactory(movieApiService)
        movieViewModel = ViewModelProvider(this, viewModelFactoryMovie).get(MovieViewModel::class.java)
        val personApiService = PersonApiService()
        viewModelFactoryPerson = PersonViewModelFactory(personApiService)
        personViewModel = ViewModelProvider(this, viewModelFactoryPerson).get(PersonViewModel::class.java)
    }

    private fun onPersonLoadingStateChanged(it: String?) {
        if (it == "LOADED"){
            binding.textPeople.visibility = View.VISIBLE
            personViewModel.searchPeopleLiveData.observe(viewLifecycleOwner, Observer {
                onPersonLoaded(it)
            })
        }else{

        }
    }

    private fun onPersonLoaded(personResponse: PersonResponse) {
        val peopleList =  personViewModel.getPeopleFromResponse(personResponse)
        updateViewsForPeople(peopleList)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvPeople.layoutManager = layoutManager
        binding.rvPeople.adapter = PersonAdapter(requireContext(), peopleList, ::personItemClick)
    }

    private fun updateViewsForPeople(peopleList: ArrayList<Person>) {
        if (peopleList.size == 0){
            binding.textPeople.visibility = View.GONE
            binding.rvPeople.visibility = View.GONE
        }else{
            binding.textPeople.visibility = View.VISIBLE
            binding.rvPeople.visibility = View.VISIBLE
        }
    }

    private fun onMovieLoadingStateChanged(it: String) {
        if (it == "LOADED"){
           movieViewModel.searchMoviesLiveData.observe(viewLifecycleOwner, Observer {
               onMovieLoaded(it)})
            println()
        }else{

        }

    }

    private fun onMovieLoaded(movieResponse: MovieResponse){
        val movieList = movieViewModel.getMoviesFromResponse(movieResponse)
        updateViewsForMovies(movieList)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = MovieAdapter(requireContext(), movieList, ::movieItemClick)
    }

    private fun updateViewsForMovies(movieList: ArrayList<Movie>) {
        if (movieList.size == 0){
            binding.textMovies.visibility = View.GONE
            binding.rvMovies.visibility = View.GONE
        }else{
            binding.textMovies.visibility = View.VISIBLE
            binding.rvMovies.visibility = View.VISIBLE
        }
    }

    private fun movieItemClick(position: Int){
        val action =
            MovieItemsFragmentDirections.actionMovieItemsFragmentToMovieDetailsFragment(movieViewModel.getMovieId(position))
        findNavController().navigate(action)
    }

    private fun personItemClick(position: Int){
        val action =
            MovieItemsFragmentDirections.actionMovieItemsFragmentToPersonDetailsFragment(personViewModel.getPersonId(position))
        findNavController().navigate(action)
    }
}


