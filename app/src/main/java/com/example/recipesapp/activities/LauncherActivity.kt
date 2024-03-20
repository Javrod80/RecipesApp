package com.example.recipesapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.recipesapp.adapter.DataRecipesAdapter
import com.example.recipesapp.data.DataRecipes
import com.example.recipesapp.data.RecipesServiceApi
import com.example.recipesapp.databinding.ActivityLauncherBinding
import com.example.recipesapp.provider.RecipeDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding


    private var dataRecipeList: List<DataRecipes> = listOf()
    private lateinit var session: SessionPreference





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Toast.makeText(this@LauncherActivity, "Downloading Data",Toast.LENGTH_SHORT ).show()






        session = SessionPreference(this)
        if (session.didFetchData) {
           // navigateToRecipesList()
        } else {
            seeRecipes(query = String())
        }


        listener()


    }





    private fun seeRecipes(query: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call: RecipesServiceApi = retrofit.create(RecipesServiceApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {


            val response = call.allRecipes()

            Log.i("HTTP", response.body().toString())

            runOnUiThread {
                if (response.body() != null) {
                    Log.i("HTTP", "Respuesta correcta")

                    var transformList = mutableListOf<DataRecipes>()

                    for (recipe in response.body()?.recipes.orEmpty()) {
                        val dataRecipes = DataRecipes(
                            recipe.id,
                            recipe.name,
                            recipe.image,
                            recipe.ingredients,
                            recipe.instructions,
                            recipe.prepTimes,
                            recipe.cookTime,
                            recipe.difficulty,
                            recipe.cuisine,
                            recipe.mealType.joinToString { it })
                        transformList.add(dataRecipes)
                    }


                    dataRecipeList = transformList.toList()

                    val recipeDAO = RecipeDAO(this@LauncherActivity)
                    recipeDAO.deleteAll()
                    for (recipe in dataRecipeList) {
                        recipeDAO.insert(recipe)
                    }

                    session.didFetchData = true
                    navigateToRecipesList()










                } else {
                    Log.i("HTTP", "Respuesta incorrecta")


                }
            }
        }
    }

    private fun listener() {
        binding.Boton1.setOnClickListener() {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }


    }
    private fun navigateToRecipesList() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}