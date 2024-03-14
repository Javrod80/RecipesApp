package com.example.recipesapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.recipesapp.Data.Recipes
import com.example.recipesapp.Data.RecipesServiceApi
import com.example.recipesapp.databinding.ActivityRecipesBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipesBinding

    private lateinit var recipes : Recipes

    private var recipeId: Int? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)



        recipeId = intent.getIntExtra("RECIPES_ID",-1)


        detailRecipes(recipeId!!)



    }

    private fun loadData() {
        Picasso.get().load(recipes.image).into(binding.imageRec)
        binding.ingredients.text = recipes.ingredients.toString()
        binding.cuisine.text = recipes.cuisine
        binding.cookTime.text = recipes.cookTime.toString()
        binding.prepTime.text = recipes.prepTimes.toString()
        binding.difficulty.text = recipes.difficulty
        binding.mealType.text = recipes.mealType.toString()
        binding.instruccion.text = recipes.instructions.toString()


    }
    private fun detailRecipes(id : Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call: RecipesServiceApi = retrofit.create(RecipesServiceApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = call.recipesId(id)

            runOnUiThread {
                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta")
                    recipes= response.body()!!
                    loadData()
                }else {
                    Log.i("HTTP","respuesta incorrecta" )
                }

            }






        }


    }







}