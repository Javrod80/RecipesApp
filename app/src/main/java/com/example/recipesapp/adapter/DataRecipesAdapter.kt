package com.example.recipesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.data.DataRecipes
import com.example.recipesapp.databinding.ItemRecipesBinding
import com.squareup.picasso.Picasso

class DataRecipesAdapter(private var dataRecipes: List<DataRecipes> = listOf(),
    val onClickListener: (position : Int)-> Unit,
    ): RecyclerView.Adapter<DataRecipesAdapter.RecipesDAOViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesDAOViewHolder {
        val binding = ItemRecipesBinding.inflate((LayoutInflater.from(parent.context)), parent, false)
        return RecipesDAOViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataRecipes.size
    }

    override fun onBindViewHolder(holder: RecipesDAOViewHolder, position: Int) {
        holder.render(dataRecipes[position])
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    fun updateDataRecipes(results: List<DataRecipes>?) {
        dataRecipes = results !!
        notifyDataSetChanged()
    }




    class RecipesDAOViewHolder (val binding: ItemRecipesBinding): RecyclerView.ViewHolder(binding.root){

        fun render (dataRecipes: DataRecipes) {

            binding.textViewRecipe.setText(dataRecipes.recipe)
            Picasso.get().load(dataRecipes.image).into(binding.recipeImageView)

        }

    }

}

