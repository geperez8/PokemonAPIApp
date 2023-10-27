package com.example.and101_week5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokeAdapter(val pokeList: MutableList<MutableList<String>>) : RecyclerView.Adapter<PokeAdapter.PokeViewHolder>() {

    class PokeViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val pokeImage: ImageView
        val pokeName: TextView
        val pokeId: TextView

        init{
            pokeImage = view.findViewById(R.id.imageHolder)
            pokeName = view.findViewById(R.id.NameText)
            pokeId = view.findViewById(R.id.IdText)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeAdapter.PokeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.poke_item, parent, false)

        return PokeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }

    override fun onBindViewHolder(holder: PokeAdapter.PokeViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(pokeList[position][2])
            .fitCenter()
            .into(holder.pokeImage)

        var Name = pokeList[position][0]

        var Id = pokeList[position][1]

        holder.pokeName.setText("Name: $Name")
        holder.pokeId.setText("ID: $Id")
    }
}