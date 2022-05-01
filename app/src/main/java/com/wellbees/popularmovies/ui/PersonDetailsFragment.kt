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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.wellbees.popularmovies.R
import com.wellbees.popularmovies.databinding.FragmentPersonDetailsBinding
import com.wellbees.popularmovies.model.PersonDetailResponse
import com.wellbees.popularmovies.service.MovieApiService
import com.wellbees.popularmovies.service.PersonApiService


class PersonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPersonDetailsBinding
    lateinit var viewModelFactoryPerson: ViewModelProvider.Factory
    private lateinit var personViewModel : PersonViewModel
    private var personId : Int = -1
    val args: PersonDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonDetailsBinding.inflate(inflater)

        personId = args.personId

        val personApiService = PersonApiService()
        viewModelFactoryPerson = PersonViewModelFactory(personApiService)
        personViewModel = ViewModelProvider(this, viewModelFactoryPerson).get(PersonViewModel::class.java)

        personViewModel.getPersonDetailsById(personId)

        personViewModel.personDetailLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onPersonDetailLoadingStateChanged(it)})

        return binding.root
    }

    private fun onPersonDetailLoadingStateChanged(it: String) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

        if (it == "LOADED"){
            personViewModel.personDetailsLiveData.observe(viewLifecycleOwner, Observer {
                onPersonDetailsLoaded(it)})


            println()
        }else{
            val y = "error"

            Toast.makeText(requireContext(), "HATAAAAAAA", Toast.LENGTH_SHORT).show()

            println()
        }
    }

    private fun onPersonDetailsLoaded(personDetailsResponse: PersonDetailResponse) {
        val baseUrl : String = "https://image.tmdb.org/t/p/original/"
        Glide.with(requireActivity())
            .load(baseUrl + personDetailsResponse.profilePath)
            .transform(RoundedCorners(30))
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error_loading_image)
            .into(binding.detailImagePerson);

        binding.tvPersonName.text = personDetailsResponse.name
        binding.tvMovieDescription.text = personDetailsResponse.biography
        binding.tvMovieDescription2.text = personDetailsResponse.birthday

        println()

        //binding.tvMovieDescription.text = movieDetailsResponse.overview
    }


}