package com.wellbees.popularmovies.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.wellbees.popularmovies.R
import com.wellbees.popularmovies.adapter.PersonAdapter
import com.wellbees.popularmovies.databinding.FragmentMovieDetailsBinding
import com.wellbees.popularmovies.model.CastResponse
import com.wellbees.popularmovies.model.MovieDetailsResponse
import com.wellbees.popularmovies.model.MovieTrailerResponse
import com.wellbees.popularmovies.service.MovieApiService
import com.wellbees.popularmovies.ui.viewmodel.MovieViewModel
import com.wellbees.popularmovies.ui.base.MovieViewModelFactory

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    lateinit var viewModelFactoryMovie: ViewModelProvider.Factory
    private lateinit var movieViewModel: MovieViewModel
    private var movieId: Int = -1
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater)

        movieId = args.movieId

        initializeViewModel()
        initializeObservers()

        return binding.root
    }

    private fun initializeObservers() {
        movieViewModel.getDetailsByMovieId(movieId)
        movieViewModel.movieDetailLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onMovieLoadingStateChanged(it)
        })

        movieViewModel.getCastOfMovie(movieId)
        movieViewModel.castLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onCastLoadingStateChanged(it)
        })

        movieViewModel.getTrailerByMovieId(movieId)
        movieViewModel.trailerLoadingStateLiveData.observe(viewLifecycleOwner, Observer {
            onTrailerLoadingStateChanged(it)
        })
    }

    private fun onCastLoadingStateChanged(it: String) {
        if (it == "LOADED") {
            movieViewModel.castLiveData.observe(viewLifecycleOwner, Observer {
                onCastLoaded(it)
            })
        } else {

        }
    }

    private fun onTrailerLoadingStateChanged(it: String) {
        if (it == "LOADED") {
            movieViewModel.trailerLiveData.observe(viewLifecycleOwner, Observer {
                onTrailerLoaded(it)
            })
        } else {

        }
    }

    private fun onTrailerLoaded(movieTrailerResponse: MovieTrailerResponse) {
        if (movieTrailerResponse.trailers.lastIndex>=0){
            initializeYoutubePlayer(movieTrailerResponse.trailers.get(movieTrailerResponse.trailers.lastIndex).key)
        }else{
            Toast.makeText(requireContext(), "Trailer is not available", Toast.LENGTH_SHORT).show()
            binding.youtubePlayerView.visibility = View.GONE
        }
    }

    private fun onCastLoaded(castResponse: CastResponse) {
        val cast = movieViewModel.getCastFromResponse(castResponse)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvCast.layoutManager = layoutManager
        binding.rvCast.adapter = PersonAdapter(requireContext(), cast, ::castItemClick)
    }

    private fun initializeViewModel() {
        val movieApiService = MovieApiService()
        viewModelFactoryMovie = MovieViewModelFactory(movieApiService)
        movieViewModel =
            ViewModelProvider(this, viewModelFactoryMovie).get(MovieViewModel::class.java)
    }

    private fun initializeYoutubePlayer(videoId: String) {
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0F)
            }
        })
    }

    private fun onMovieLoadingStateChanged(it: String) {
        if (it == "LOADED") {
            movieViewModel.movieDetailsLiveData.observe(viewLifecycleOwner, Observer {
                onMovieDetailsLoaded(it)
            })
        } else {

        }
    }

    private fun onMovieDetailsLoaded(movieDetailsResponse: MovieDetailsResponse) {
        val baseUrl: String = "https://image.tmdb.org/t/p/original/"
        Glide.with(requireActivity())
            .load(baseUrl + movieDetailsResponse.posterPath)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error_loading_image)
            .into(binding.detailImageMovie);
        binding.tvMovieTitle.text = movieDetailsResponse.title
        binding.tvMovieDescription.text = movieDetailsResponse.overview
        binding.tvMovieReleaseDate.text = movieDetailsResponse.releaseDate
        binding.tvMovieRating.text = movieDetailsResponse.voteAverage.toString()
    }

    private fun castItemClick(position: Int) {
        val action =
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(
                movieViewModel.getPersonIdFromCast(position)
            )
        findNavController().navigate(action)
    }

}