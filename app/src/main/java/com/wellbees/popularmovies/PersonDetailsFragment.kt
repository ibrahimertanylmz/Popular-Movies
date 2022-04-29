package com.wellbees.popularmovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wellbees.popularmovies.databinding.FragmentPersonDetailsBinding


class PersonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPersonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonDetailsBinding.inflate(inflater)

        return binding.root
    }

}