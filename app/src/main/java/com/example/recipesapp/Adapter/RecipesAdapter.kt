package com.example.recipesapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.Data.Recipes
import com.example.recipesapp.databinding.ItemRecipesBinding
import com.squareup.picasso.Picasso

class RecipesAdapter (private var items: List<Recipes> = listOf(), val onClickListener :(position: Int) -> Unit):RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val binding = ItemRecipesBinding.inflate((LayoutInflater.from(parent.context)),parent,false)
        return RecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.render(items[position])
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }


    fun updateItems(results: List<Recipes>?) {
        items = results!!
        notifyDataSetChanged()
    }






    override fun getItemCount(): Int {
        return items.size
    }




    class RecipesViewHolder(val binding: ItemRecipesBinding):
    RecyclerView.ViewHolder(binding.root){

        fun render (recipes: Recipes){

            binding.textViewRecipe.text = recipes.name
            Picasso.get().load(recipes.image.url).into(binding.recipeImageView)
        }

    }





}