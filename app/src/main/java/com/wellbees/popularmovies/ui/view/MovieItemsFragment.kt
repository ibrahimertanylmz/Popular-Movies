package com.wellbees.popularmovies.ui.view

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
import com.wellbees.popularmovies.adapter.GenreAdapter
import com.wellbees.popularmovies.adapter.MovieAdapter
import com.wellbees.popularmovies.adapter.PersonAdapter
import com.wellbees.popularmovies.databinding.FragmentMovieItemsBinding
import com.wellbees.popularmovies.model.*
import com.wellbees.popularmovies.service.MovieApiService
import com.wellbees.popularmovies.service.PersonApiService
import com.wellbees.popularmovies.ui.viewmodel.MovieViewModel
import com.wellbees.popularmovies.ui.viewmodel.PersonViewModel
import com.wellbees.popularmovies.ui.base.MovieViewModelFactory
import com.wellbees.popularmovies.ui.base.PersonViewModelFactory

class MovieItemsFragment : Fragment() {

    private lateinit var binding: FragmentMovieItemsBinding
    lateinit var viewModelFactoryMovie: ViewModelProvider.Factory
    private lateinit var movieViewModel: MovieViewModel
    lateinit var viewModelFactoryPerson: ViewModelProvider.Factory
    private lateinit var personViewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieItemsBinding.inflate(inflater)

        initializeViewModels()
        initializeSearchEvents()
        observeLiveDatas()

        return binding.root
    }

    private fun initializeSearchEvents() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.length > 2) {
                        movieViewModel.onSearchQuery(binding.edtSearch.text.toString())
                        personViewModel.onSearchQuery(binding.edtSearch.text.toString())
                        movieViewModel.getGenres()
                    } else {
                        updateViewsForMovies(0)
                        updateViewsForPeople(0)
                        updateViewsForGenre(0)
                    }
                }
            }
        })
    }

    private fun observeLiveDatas() {
        movieViewModel.movieLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onMovieLoadingStateChanged(it)
        })

        personViewModel.personLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onPersonLoadingStateChanged(it)
        })

        movieViewModel.genreLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onGenreLoadingStateChanged(it)
        })
    }

    private fun initializeViewModels() {
        val movieApiService = MovieApiService()
        viewModelFactoryMovie = MovieViewModelFactory(movieApiService)
        movieViewModel =
            ViewModelProvider(this, viewModelFactoryMovie).get(MovieViewModel::class.java)
        val personApiService = PersonApiService()
        viewModelFactoryPerson = PersonViewModelFactory(personApiService)
        personViewModel =
            ViewModelProvider(this, viewModelFactoryPerson).get(PersonViewModel::class.java)
    }

    private fun onPersonLoadingStateChanged(it: LoadState?) {
        if (it == LoadState.Loaded) {
            binding.textPeople.visibility = View.VISIBLE
            personViewModel.searchPeopleLiveData.observe(viewLifecycleOwner, Observer {
                onPersonLoaded(it)
            })
        } else {

        }
    }

    private fun onMovieLoadingStateChanged(it: LoadState) {
        if (it == LoadState.Loaded) {
            movieViewModel.searchMoviesLiveData.observe(viewLifecycleOwner, Observer {
                onMovieLoaded(it)
            })
            println()
        } else {

        }
    }

    private fun onGenreLoadingStateChanged(it: LoadState) {
        if (it == LoadState.Loaded) {
            movieViewModel.genreLiveData.observe(viewLifecycleOwner, Observer {
                onGenreLoaded(it)
            })
            println()
        } else {

        }
    }

    private fun onGenreLoaded(genreResponse: GenreResponse) {
        val genreList =
            movieViewModel.getFilteredGenreList(genreResponse, binding.edtSearch.text.toString())
        updateViewsForGenre(genreList.size)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvGenre.layoutManager = layoutManager
        binding.rvGenre.adapter = GenreAdapter(requireContext(), genreList)
    }

    private fun onPersonLoaded(personResponse: PersonResponse) {
        val peopleList = personViewModel.getPeopleFromResponse(personResponse)
        updateViewsForPeople(peopleList.size)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvPeople.layoutManager = layoutManager
        binding.rvPeople.adapter = PersonAdapter(requireContext(), peopleList, ::personItemClick)
    }

    private fun onMovieLoaded(movieResponse: MovieResponse) {
        val movieList = movieViewModel.getMoviesFromResponse(movieResponse)
        updateViewsForMovies(movieList.size)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = MovieAdapter(requireContext(), movieList, ::movieItemClick)
    }

    private fun updateViewsForGenre(size: Int) {
        if (size == 0) {
            binding.textGenre.visibility = View.GONE
            binding.rvGenre.visibility = View.GONE
        } else {
            binding.textGenre.visibility = View.VISIBLE
            binding.rvGenre.visibility = View.VISIBLE
        }
    }

    private fun updateViewsForPeople(size: Int) {
        if (size == 0) {
            binding.textPeople.visibility = View.GONE
            binding.rvPeople.visibility = View.GONE
        } else {
            binding.textPeople.visibility = View.VISIBLE
            binding.rvPeople.visibility = View.VISIBLE
        }
    }

    private fun updateViewsForMovies(size: Int) {
        if (size == 0) {
            binding.textMovies.visibility = View.GONE
            binding.rvMovies.visibility = View.GONE
        } else {
            binding.textMovies.visibility = View.VISIBLE
            binding.rvMovies.visibility = View.VISIBLE
        }
    }

    private fun movieItemClick(position: Int) {
        val action =
            MovieItemsFragmentDirections.actionMovieItemsFragmentToMovieDetailsFragment(
                movieViewModel.getMovieId(position)
            )
        findNavController().navigate(action)
    }

    private fun personItemClick(position: Int) {
        val action =
            MovieItemsFragmentDirections.actionMovieItemsFragmentToPersonDetailsFragment(
                personViewModel.getPersonId(position)
            )
        findNavController().navigate(action)
    }
}


