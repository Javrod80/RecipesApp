package com.example.recipesapp.Activities

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
import com.example.recipesapp.Adapter.DataRecipesAdapter
import com.example.recipesapp.Adapter.RecipesAdapter
import com.example.recipesapp.Data.DataRecipes
import com.example.recipesapp.Data.Recipes
import com.example.recipesapp.Data.RecipesServiceApi
import com.example.recipesapp.R
import com.example.recipesapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: RecipesAdapter
    private var recipesList : List <Recipes> = listOf()



    private lateinit var dataAdapter : DataRecipesAdapter
    private var datarecipesList : List <DataRecipes> = listOf()







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.searchView.setOnQueryTextListener(this)

        initRecyledView()




    }

    private fun initRecyledView() {
        adapter = RecipesAdapter(recipesList){
            onItemClickListener(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

       /* dataAdapter = DataRecipesAdapter(datarecipesList){
            onItemClickListener(it)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = dataAdapter */
    }

    private fun onItemClickListener(position: Int) {
        val recipes : Recipes = recipesList[position]

        val intent = Intent(this, RecipesActivity::class.java)
        intent.putExtra("RECIPES_ID", recipes.id)

        //val detailRecipe = Intent (this,DetailActivity::class.java)
        //detailRecipe.putExtra("RECIPES_ID",recipes.id)




        startActivity(intent)
       // startActivity(detailRecipe)



    }






    /*private fun searchRecipes (query: String) {


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
                    recipesList = response.body()?.recipes.orEmpty()
                    adapter.updateItems(recipesList)



                } else {
                    Log.i("HTTP", "Respuesta incorrecta")
                    showError()


                }
                hideKeyboard()
            }
        }
    }*/

    // LLamada a todas las recetas

    private fun seeRecipes(query: String) {

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

                   // datarecipesList = response.body()?..orEmpty()
                    //dataAdapter.updateDataRecipes(datarecipesList)




                } else {
                    Log.i("HTTP", "Respuesta incorrecta")
                    showError()


                }
                hideKeyboard()
            }
        }

    }
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
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
    }

    //LLamada buscar recetas

   /* override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchRecipes(query)
        }
        return true
    }
    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }*/



    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean { //barra

        when (item.itemId) {

            android.R.id.home -> {showExitDialog()
               // finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

//MaterialAlertDialogBuilder

    private fun showExitDialog() {
        val builder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(this,
            R.style.AlertDialogTheme
        )
            //.setIcon(R.drawable.arrow_back)
            .setTitle(R.string.close_application)
            .setMessage(R.string.are_you_sure)
            .setPositiveButton(R.string.go_out) { _, _ -> finish() }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog?.cancel() }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }














}