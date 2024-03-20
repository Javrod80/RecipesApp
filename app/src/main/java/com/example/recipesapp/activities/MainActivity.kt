package com.example.recipesapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.adapter.DataRecipesAdapter
import com.example.recipesapp.adapter.RecipesAdapter
import com.example.recipesapp.data.DataRecipes
import com.example.recipesapp.data.Recipes
import com.example.recipesapp.data.RecipesServiceApi
import com.example.recipesapp.provider.RecipeDAO
import com.example.recipesapp.R
import com.example.recipesapp.databinding.ActivityMainBinding

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecipesAdapter
    private var recipesList: List<Recipes> = listOf()


     private lateinit var dataAdapter: DataRecipesAdapter
     private var dataRecipeList : List<DataRecipes> = listOf()
    private lateinit var recipeDAO : RecipeDAO






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.searchView.setOnQueryTextListener(this)


        recipeDAO = RecipeDAO(this)

       // initRecyledView()

          initRecycleData()


    }


    //

   /* private fun initRecyledView() {
        adapter = RecipesAdapter(recipesList) {
            onItemClickListener(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


    }

    private fun onItemClickListener(position: Int) {
        val recipes: Recipes = recipesList[position]

        val intent = Intent(this, RecipesActivity::class.java)
        intent.putExtra("RECIPES_ID", recipes.id)

        startActivity(intent)

    }*/


//RecycleView de la tabla


     private fun initRecycleData (){

         dataRecipeList = recipeDAO.findAll()

         dataAdapter = DataRecipesAdapter(dataRecipeList){
             onItemDataListener(it)
         }
         binding.recyclerView.layoutManager = LinearLayoutManager(this)
         binding.recyclerView.adapter = dataAdapter

     }


     private fun onItemDataListener (position: Int){
         val dataRecipes : DataRecipes = dataRecipeList[position]
         val intent = Intent (this,RecipesActivity::class.java)
         intent.putExtra("RECIPES_ID",dataRecipes.id)

          startActivity(intent)

     }



    //Buscar recetas


    private fun searchRecipes (query: String) {




        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call: RecipesServiceApi = retrofit.create(RecipesServiceApi::class.java)



        CoroutineScope(Dispatchers.IO).launch {
            val response = call.searchByName(query)


            runOnUiThread {
                if (response.body() != null) {
                    Log.i("HTTP", "Respuesta correcta")

                   //recipesList = response.body()?.recipes.orEmpty()
                   //adapter.updateItems(recipesList)


                    // Transform from Recipes to DataRecipes


                    var transformList = mutableListOf<DataRecipes>()

                    for (recipe in response.body()?.recipes.orEmpty()) {
                        val dataRecipes = DataRecipes(recipe.id, recipe.name, recipe.image, recipe.ingredients, recipe.instructions, recipe.prepTimes, recipe.cookTime, recipe.difficulty, recipe.cuisine, recipe.mealType.joinToString { it })
                        transformList.add(dataRecipes)
                    }


                    dataRecipeList = transformList.toList()
                    dataAdapter.updateDataRecipes(dataRecipeList)






                } else {
                    Log.i("HTTP", "Respuesta incorrecta")
                    showError()


                }
                hideKeyboard()
            }
        }
    }
    //LLamada buscar recetas

     override fun onQueryTextSubmit(query: String?): Boolean {
         if (!query.isNullOrEmpty()) {
             searchRecipes(query)
         }
         return true
     }
     override fun onQueryTextChange(p0: String?): Boolean {
         return true
     }


    // LLamada a todas las recetas

   /* private fun seeRecipes(query: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call: RecipesServiceApi = retrofit.create(RecipesServiceApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            // Log.i("HTTP", "Antes de la llamada")
            val response = call.allRecipes()
            // Log.i("HTTP", "Despues de la llamada")
            Log.i("HTTP", response.body().toString())

            runOnUiThread {
                if (response.body() != null) {
                    Log.i("HTTP", "Respuesta correcta")

                    recipesList = response.body()?.recipes.orEmpty()
                    adapter.updateItems(recipesList)

                    // dataRecipesList = response.body()?.recipes.orEmpty()
                    // dataAdapter.updateDataRecipes(dataRecipesList)


                } else {
                    Log.i("HTTP", "Respuesta incorrecta")
                    showError()


                }
                hideKeyboard()
            }
        }

    }


    // Llamada a todas las recetas

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            seeRecipes(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }*/

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }


    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //barra

        when (item.itemId) {

            android.R.id.home -> {
                showExitDialog()
                // finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

//MaterialAlertDialogBuilder

    private fun showExitDialog() {
        val builder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(
            this,
            R.style.AlertDialogTheme
        )
            //.setIcon(R.drawable.arrow_back)
            .setTitle(resources.getString(R.string.close_application))
            .setMessage(resources.getString(R.string.are_you_sure))
            .setPositiveButton(resources.getString(R.string.go_out)) { _, _ -> finish() }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ -> dialog?.cancel() }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}