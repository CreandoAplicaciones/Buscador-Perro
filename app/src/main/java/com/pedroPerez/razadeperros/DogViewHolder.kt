package com.pedroPerez.razadeperros

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pedroPerez.razadeperros.databinding.ItemDogBinding

class DogViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding=ItemDogBinding.bind(view)

    fun bind(image:String){

        Glide.with(binding.imageViewDog).load(image).into(binding.imageViewDog
        )

    }
}