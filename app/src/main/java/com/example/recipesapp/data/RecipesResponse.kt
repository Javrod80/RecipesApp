package com.example.recipesapp.data

import com.google.gson.annotations.SerializedName

class RecipesResponse(

    @SerializedName("recipes") val recipes: List<Recipes>,


    ) {

}
class Recipes(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("instructions") val instructions: List<String>,
    @SerializedName("prepTimeMinutes") val prepTimes: Int,
    @SerializedName("cookTimeMinutes") val cookTime: Int,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("cuisine") val cuisine: String,
    @SerializedName("mealType") val mealType: List<String>,
    @SerializedName("image") val image: String

)




class Image(
    @SerializedName("url") val url: String
) {

}
