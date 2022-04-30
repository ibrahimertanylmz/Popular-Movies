package com.wellbees.popularmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.wellbees.popularmovies.databinding.FragmentMovieItemsBinding
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.model.MovieViewModel

class MovieItemsFragment : Fragment() {

    private lateinit var binding: FragmentMovieItemsBinding
    private var movieViewModel = MovieViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieItemsBinding.inflate(inflater)
        //movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)



        //onTextChanged
        movieViewModel.onSearchQuery("eve")

        movieViewModel.movieLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onMovieLoadingStateChanged(it)})


        binding.btnToMovieDetails.setOnClickListener {
            val action = MovieItemsFragmentDirections.actionMovieItemsFragmentToMovieDetailsFragment(2)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun onMovieLoadingStateChanged(it: String) {

        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        if (it == "LOADED"){
           movieViewModel.searchMoviesLiveData.observe(viewLifecycleOwner, Observer {
               onLoaded(it)})










            println()
        }else{
            val y = "error"

            Toast.makeText(requireContext(), "HATAAAAAAA", Toast.LENGTH_SHORT).show()

            println()
        }

    }

    fun onLoaded(movie: Movie){
        movie.results[0].name

        movie.results[1].name


        movie.results[2].name





    }

}


