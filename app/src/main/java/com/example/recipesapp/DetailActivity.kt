package com.example.recipesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.recipesapp.Data.Recipes
import com.example.recipesapp.Data.RecipesServiceApi
import com.example.recipesapp.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding


    private lateinit var recipes: Recipes


    private var recipeId: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recipeId = intent.getIntExtra("RECIPES_ID", -1)

        loadData()
        detailRecipes(recipeId!!)
        passData()


    }

    private fun loadData() {
        Picasso.get().load(recipes.image).into(binding.imageRec2)
        binding.ingredients.text = recipes.ingredients.toString()
        binding.instruccion.text = recipes.instructions.toString()
        binding.recipeName2.text = recipes.name


    }


    private fun detailRecipes(id: Int) {
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
                    recipes = response.body()!!
                    loadData()
                } else {
                    Log.i("HTTP", "respuesta incorrecta")
                }

            }


        }


    }


    private fun passData() {


        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("RECIPES_ID", recipes.id)


        startActivity(intent)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //barra
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}