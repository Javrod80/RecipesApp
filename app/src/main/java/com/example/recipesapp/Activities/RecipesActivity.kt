package com.example.recipesapp.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

import com.example.recipesapp.Data.Recipes
import com.example.recipesapp.Data.RecipesServiceApi
import com.example.recipesapp.R
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

    private lateinit var session : SessionPreference
    private var isFavorite:Boolean = false
    private var favoriteMenuItem: MenuItem? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        session = SessionPreference(this)



        recipeId = intent.getIntExtra("RECIPES_ID",-1)


        detailRecipes(recipeId!!)









    }



    private fun loadData() {
        Picasso.get().load(recipes.image).into(binding.imageRec)
        //binding.ingredients.text = "INGREDIENTS :  ${recipes.ingredients.toString()}"
        binding.cuisine.text = "CUISINE : ${recipes.cuisine}"
        binding.cookTime.text = "COOK TIME : ${recipes.cookTime.toString()}"
        binding.prepTime.text = "PREP TIME : ${recipes.prepTimes.toString()}"
        binding.difficulty.text ="DIFFICULTY : ${recipes.difficulty}"
        binding.mealType.text ="MEALTIME : ${recipes.mealType.toString()}"
        //binding.instrution.text = "INSTRUCTION : ${recipes.instructions.toString()}"
        binding.recipeName.text =  recipes.name



        var ingredientsText = ""
        recipes.ingredients.forEachIndexed { index, element ->
            if (index > 0) ingredientsText += "\n"
            ingredientsText += " - $element -"
        }
        binding.ingredients.text =  ingredientsText

        var instructionText = ""
        recipes.instructions.forEachIndexed { index, element ->
            if (index > 0) instructionText += "\n"
            instructionText += "  $element "
        }
        binding.instruction.text =  instructionText









        isFavorite = recipes.id.toString() == session.favoriteRecipe

        setFavoriteIcon()


    }









    // LLamada a detalles de  las recetas by Id
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)
        favoriteMenuItem = menu?.findItem(R.id.menu_favorite)
        setFavoriteIcon()


        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //barra
        when (item.itemId) {
            android.R.id.home -> {
                finish()
               return true
            }

            //shared

        R.id.compartir -> {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, R.string.shared)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

            //  favorite

            R.id.menu_favorite -> {
                isFavorite = !isFavorite
                if (isFavorite) {
                    session.favoriteRecipe = recipes.id.toString()
                } else {
                    session.setFavoriteRecipeValue("")
                }
                setFavoriteIcon()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }



    private fun setFavoriteIcon (){
        val favoriteIcon = if (isFavorite) {
            R.drawable.favorite_selected


        }else {
            R.drawable.favorite_unselected
        }
        favoriteMenuItem?.setIcon(favoriteIcon);

    }













}