package com.wellbees.popularmovies.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellbees.popularmovies.adapter.MovieAdapter
import com.wellbees.popularmovies.adapter.PersonAdapter
import com.wellbees.popularmovies.databinding.FragmentMovieItemsBinding
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.model.MovieResponse
import com.wellbees.popularmovies.model.PersonResponse
import com.wellbees.popularmovies.service.MovieApiService
import com.wellbees.popularmovies.service.PersonApiService

class MovieItemsFragment : Fragment() {

    private lateinit var binding: FragmentMovieItemsBinding
    lateinit var viewModelFactoryMovie: ViewModelProvider.Factory
    private lateinit var movieViewModel : MovieViewModel

    lateinit var viewModelFactoryPerson: ViewModelProvider.Factory
    private lateinit var personViewModel: PersonViewModel

    private var movieList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieItemsBinding.inflate(inflater)
        //movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        val movieApiService = MovieApiService()
        viewModelFactoryMovie = MovieViewModelFactory(movieApiService)
        movieViewModel = ViewModelProvider(this, viewModelFactoryMovie).get(MovieViewModel::class.java)

        val personApiService = PersonApiService()
        viewModelFactoryPerson = PersonViewModelFactory(personApiService)
        personViewModel = ViewModelProvider(this, viewModelFactoryPerson).get(PersonViewModel::class.java)



        //onTextChanged

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.length > 3){
                        movieViewModel.onSearchQuery(binding.edtSearch.text.toString())
                        personViewModel.onSearchQuery(binding.edtSearch.text.toString())
                    }
                }
            }
        })


        movieViewModel.movieLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onMovieLoadingStateChanged(it)})

        personViewModel.personLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onPersonLoadingStateChanged(it)})


        binding.btnToMovieDetails.setOnClickListener {
            val action =
                MovieItemsFragmentDirections.actionMovieItemsFragmentToMovieDetailsFragment(2)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun onPersonLoadingStateChanged(it: String?) {
        if (it == "LOADED"){
            personViewModel.searchPeopleLiveData.observe(viewLifecycleOwner, Observer {
                onPersonLoaded(it)


                it.results.size

                it.results

                it

                it


            })




            println()
        }else{
            val y = "error"

            Toast.makeText(requireContext(), "HATAAAAAAA", Toast.LENGTH_SHORT).show()

            println()
        }

    }

    private fun onPersonLoaded(personResponse: PersonResponse) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvPeople.layoutManager = layoutManager
        binding.rvPeople.adapter = PersonAdapter(requireContext(), personViewModel.getPeopleFromResponse(personResponse), ::personItemClick)


        val x = personViewModel.getPeopleFromResponse(personResponse)


        x

        println()

    }

    private fun onMovieLoadingStateChanged(it: String) {

        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        if (it == "LOADED"){
           movieViewModel.searchMoviesLiveData.observe(viewLifecycleOwner, Observer {
               onMovieLoaded(it)})


            println()
        }else{
            val y = "error"

            Toast.makeText(requireContext(), "HATAAAAAAA", Toast.LENGTH_SHORT).show()

            println()
        }

    }

    private fun onMovieLoaded(movieResponse: MovieResponse){
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvMovies.layoutManager = layoutManager
        movieList = movieViewModel.getMoviesFromResponse(movieResponse)
        binding.rvMovies.adapter = MovieAdapter(requireContext(), movieList, ::movieItemClick) // film yuklemede hata olursa sorun burada
    }

    private fun movieItemClick(position: Int){

        movieList[position].id

        val action =
            MovieItemsFragmentDirections.actionMovieItemsFragmentToMovieDetailsFragment(movieList[position].id)
        findNavController().navigate(action)
    }

    private fun personItemClick(position: Int){

    }



}


