package com.wellbees.popularmovies.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wellbees.popularmovies.R
import com.wellbees.popularmovies.databinding.PersonItemBinding
import com.wellbees.popularmovies.model.Person

class PersonViewHolder(
    var context: Context,
    private val binding: PersonItemBinding,
    var itemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    val baseUrl: String = "https://image.tmdb.org/t/p/original/"

    init {
        itemView.setOnClickListener {
            itemClick(adapterPosition)
        }
    }

    fun bindData(person: Person) {
        Glide.with(context)
            .load(baseUrl + person.profilePath)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_person)
            .into(binding.imagePerson);
        binding.tvName.text = person.name
    }
}