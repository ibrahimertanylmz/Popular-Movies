package com.wellbees.popularmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.wellbees.popularmovies.databinding.FragmentMovieItemsBinding

class MovieItemsFragment : Fragment() {

    private lateinit var binding: FragmentMovieItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieItemsBinding.inflate(inflater)

        binding.btnToMovieDetails.setOnClickListener {
            val action = MovieItemsFragmentDirections.actionMovieItemsFragmentToMovieDetailsFragment(2)
            findNavController().navigate(action)
        }

        return binding.root
    }

}