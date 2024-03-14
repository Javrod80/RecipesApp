package com.example.recipesapp.Data

import org.intellij.lang.annotations.Identifier
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesServiceApi {



    @GET("recipes/{id}")
    suspend fun recipesId (@Path("id") identifier: Int) : Response<Recipes>

    @GET("recipes")
    suspend fun allRecipes () : Response<RecipesResponse>




}


//@Query("q") query: String)