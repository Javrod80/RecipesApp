package com.example.recipesapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesServiceApi {



    @GET("recipes/{id}")
    suspend fun recipesId (@Path("id") identifier: Int) : Response<Recipes>
    //'https://dummyjson.com/recipes/1'

    @GET("recipes")
    suspend fun allRecipes () : Response<RecipesResponse>
    //('https://dummyjson.com/recipes')


    @GET ("recipes/search")
    suspend fun searchByName (@Query("q") query: String) : Response <RecipesResponse>

    //'https://dummyjson.com/recipes/search?q=Margherita')







}


