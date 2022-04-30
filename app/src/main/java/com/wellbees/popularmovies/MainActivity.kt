package com.wellbees.popularmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wellbees.popularmovies.databinding.ActivityMainBinding
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.service.MovieAPİ
import com.wellbees.popularmovies.service.MovieApiService
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)











    }



}