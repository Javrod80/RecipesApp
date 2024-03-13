package com.example.recipesapp.Data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesServiceApi {



    @GET("https://dummyjson.com/{recipes}")
    suspend fun serchByRecipe(@Path("recipes") query : String) : Response<RecipesResponse>


    @GET("https://dummyjson.com/recipes/search{name}")
    suspend fun recipesId (@Path("name") identifier: Int) : Response<Recipes>



}
