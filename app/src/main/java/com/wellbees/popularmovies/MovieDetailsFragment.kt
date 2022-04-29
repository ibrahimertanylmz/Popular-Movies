package com.wellbees.popularmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wellbees.popularmovies.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieDetailsBinding.inflate(inflater)


        return binding.root
    }

}