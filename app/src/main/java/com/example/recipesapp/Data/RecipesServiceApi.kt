package com.example.recipesapp.Data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesServiceApi {



    @GET("recipes/{id}")
    suspend fun recipesId (@Path("id") identifier: Int) : Response<Recipes>

    @GET("recipes")
    suspend fun allRecipes () : Response<RecipesResponse>


    @GET ("recipes/search")
    suspend fun searchByName (@Query("q") query: String) : Response <RecipesResponse>



    @GET("recipes/{id}")
    suspend fun detailId (@Path("id") identifier : Int) : Response <Recipes>




}


//@Query("q") query: String)