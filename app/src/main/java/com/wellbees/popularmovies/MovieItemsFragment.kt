package com.wellbees.popularmovies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellbees.popularmovies.adapter.MovieAdapter
import com.wellbees.popularmovies.databinding.FragmentMovieItemsBinding
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.model.MovieResponse
import com.wellbees.popularmovies.model.MovieViewModel

class MovieItemsFragment : Fragment() {

    private lateinit var binding: FragmentMovieItemsBinding
    private var movieViewModel = MovieViewModel()
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



        //onTextChanged

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.length > 3){
                        movieViewModel.onSearchQuery(binding.edtSearch.text.toString())
                    }
                }
            }
        })


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

    private fun onLoaded(movieResponse: MovieResponse){
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvMovies.layoutManager = layoutManager
        binding.rvMovies.adapter = MovieAdapter(requireContext(), movieViewModel.getMoviesFromResponse(movieResponse), ::itemClick)
    }

    private fun itemClick(position: Int){

    }

}


