package com.wellbees.popularmovies.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.wellbees.popularmovies.databinding.GenreItemBinding
import com.wellbees.popularmovies.model.Genre

class GenreViewHolder(
    var context: Context,
    private val binding: GenreItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(genre: Genre) {
        binding.tvGenre.text = genre.name
    }
}