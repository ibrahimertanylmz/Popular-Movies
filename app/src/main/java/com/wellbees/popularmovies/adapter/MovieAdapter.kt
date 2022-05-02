package com.wellbees.popularmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellbees.popularmovies.databinding.MovieItemBinding
import com.wellbees.popularmovies.model.Movie
import com.wellbees.popularmovies.viewholder.MovieViewHolder

class MovieAdapter(
    var context: Context,
    var liste: ArrayList<Movie>,
    var itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val from = LayoutInflater.from(context)
        val binding = MovieItemBinding.inflate(from, parent, false)
        return MovieViewHolder(context, binding, itemClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(liste.get(position))
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}