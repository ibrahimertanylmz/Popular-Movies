package com.wellbees.popularmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellbees.popularmovies.databinding.GenreItemBinding
import com.wellbees.popularmovies.model.Genre
import com.wellbees.popularmovies.viewholder.GenreViewHolder

class GenreAdapter(
    var context: Context,
    var liste: ArrayList<Genre>
) : RecyclerView.Adapter<GenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val from = LayoutInflater.from(context)
        val binding = GenreItemBinding.inflate(from, parent, false)
        return GenreViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bindData(liste.get(position))
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}