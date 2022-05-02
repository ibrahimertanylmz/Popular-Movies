package com.wellbees.popularmovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wellbees.popularmovies.databinding.PersonItemBinding
import com.wellbees.popularmovies.model.Person
import com.wellbees.popularmovies.viewholder.PersonViewHolder

class PersonAdapter(
    var context: Context,
    var liste: ArrayList<Person>,
    var itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<PersonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val from = LayoutInflater.from(context)
        val binding = PersonItemBinding.inflate(from, parent, false)
        return PersonViewHolder(context, binding, itemClick)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bindData(liste.get(position))
    }

    override fun getItemCount(): Int {
        return liste.size
    }
}