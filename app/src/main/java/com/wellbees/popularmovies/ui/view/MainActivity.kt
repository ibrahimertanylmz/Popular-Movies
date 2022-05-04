package com.wellbees.popularmovies.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wellbees.popularmovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setContentView(binding.root)
    }
}