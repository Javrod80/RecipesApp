package com.example.recipesapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipesapp.Adapter.RecipesAdapter
import com.example.recipesapp.Data.Recipes
import com.example.recipesapp.Data.RecipesServiceApi
import com.example.recipesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: RecipesAdapter
    private var recipesList : List <Recipes> = listOf()





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
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
    }

    private fun onItemClickListener(position: Int) {
        val recipes : Recipes = recipesList[position]

    }

    private fun seeRecipes(query: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val call: RecipesServiceApi = retrofit.create(RecipesServiceApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = call.allRecipes(query)

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

    }
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            seeRecipes(query)
        }
        return true
    }
    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
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