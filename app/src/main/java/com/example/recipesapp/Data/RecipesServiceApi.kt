package com.example.recipesapp.Data

import android.adservices.common.AdTechIdentifier
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesServiceApi {



    @GET("recipes/search")
    suspend fun searchRecipes (@Query("Key") query : String) : Response<RecipesResponse>

    @GET("{recipes}")
    suspend fun allRecipes (@Path("recipes") query: String) : Response<RecipesResponse>








}
