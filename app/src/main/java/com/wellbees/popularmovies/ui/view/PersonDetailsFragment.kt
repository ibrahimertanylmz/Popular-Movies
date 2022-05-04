package com.wellbees.popularmovies.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.wellbees.popularmovies.R
import com.wellbees.popularmovies.databinding.FragmentPersonDetailsBinding
import com.wellbees.popularmovies.model.LoadState
import com.wellbees.popularmovies.model.PersonDetailResponse
import com.wellbees.popularmovies.service.PersonApiService
import com.wellbees.popularmovies.ui.viewmodel.PersonViewModel
import com.wellbees.popularmovies.ui.base.PersonViewModelFactory

class PersonDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPersonDetailsBinding
    lateinit var viewModelFactoryPerson: ViewModelProvider.Factory
    private lateinit var personViewModel: PersonViewModel
    private var personId: Int = -1
    val args: PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonDetailsBinding.inflate(inflater)

        personId = args.personId

        initializeViewModel()
        initializeObserver()

        return binding.root
    }

    private fun initializeObserver() {
        personViewModel.getPersonDetailsById(personId)
        personViewModel.personDetailLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onPersonDetailLoadingStateChanged(it)
        })
    }

    private fun initializeViewModel() {
        val personApiService = PersonApiService()
        viewModelFactoryPerson = PersonViewModelFactory(personApiService)
        personViewModel =
            ViewModelProvider(this, viewModelFactoryPerson).get(PersonViewModel::class.java)
    }

    private fun onPersonDetailLoadingStateChanged(it: LoadState) {
        if (it == LoadState.Loaded) {
            personViewModel.personDetailsLiveData.observe(viewLifecycleOwner, Observer {
                onPersonDetailsLoaded(it)
            })
        } else {

        }
    }

    private fun onPersonDetailsLoaded(personDetailsResponse: PersonDetailResponse) {
        val baseUrl: String = "https://image.tmdb.org/t/p/original/"
        Glide.with(requireActivity())
            .load(baseUrl + personDetailsResponse.profilePath)
            .transform(RoundedCorners(30))
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error_loading_image)
            .into(binding.detailImagePerson);
        binding.tvPersonName.text = personDetailsResponse.name
        binding.tvPersonBiography.text = personDetailsResponse.biography
        (personDetailsResponse.birthday + " - " + personDetailsResponse.placeOfBirth).also { binding.tvPersonDate.text = it }
    }
}