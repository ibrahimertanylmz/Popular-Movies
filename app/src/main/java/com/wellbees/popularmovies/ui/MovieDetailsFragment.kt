package com.wellbees.popularmovies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.wellbees.popularmovies.R
import com.wellbees.popularmovies.databinding.FragmentMovieDetailsBinding
import com.wellbees.popularmovies.model.MovieDetailsResponse
import com.wellbees.popularmovies.service.MovieApiService

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    lateinit var viewModelFactoryMovie: ViewModelProvider.Factory
    private lateinit var movieViewModel : MovieViewModel
    private var movieId : Int = -1
    val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentMovieDetailsBinding.inflate(inflater)

        movieId = args.detailval

        val movieApiService = MovieApiService()
        viewModelFactoryMovie = MovieViewModelFactory(movieApiService)
        movieViewModel = ViewModelProvider(this, viewModelFactoryMovie).get(MovieViewModel::class.java)

        movieViewModel.getDetailsByMovieId(movieId)

        movieViewModel.movieDetailLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onMovieLoadingStateChanged(it)})


        return binding.root
    }

    private fun onMovieLoadingStateChanged(it: String) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        if (it == "LOADED"){
            movieViewModel.movieDetailsLiveData.observe(viewLifecycleOwner, Observer {
                onMovieDetailsLoaded(it)})


            println()
        }else{
            val y = "error"

            Toast.makeText(requireContext(), "HATAAAAAAA", Toast.LENGTH_SHORT).show()

            println()
        }
    }

    private fun onMovieDetailsLoaded(movieDetailsResponse: MovieDetailsResponse) {
        val baseUrl : String = "https://image.tmdb.org/t/p/original/"
        Glide.with(requireActivity())
            .load(baseUrl + movieDetailsResponse.posterPath)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error_loading_image)
            .into(binding.detailImageMovie);
        binding.tvMovieTitle.text = movieDetailsResponse.title
        binding.tvMovieDescription.text = movieDetailsResponse.overview

        if(movieDetailsResponse.video == true){
            Toast.makeText(requireContext(), movieId, Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(), "no videooooooooooooo", Toast.LENGTH_LONG).show()

        }
    }

}