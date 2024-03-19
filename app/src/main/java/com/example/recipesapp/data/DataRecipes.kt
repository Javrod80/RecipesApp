package com.example.recipesapp.data

import com.example.recipesapp.utils.DatabaseManager

class DataRecipes(
    var id: Int,
    var recipe: String,
    var image: String,
    var ingredients: List<String>,
    var instruction: List<String>,
    var prepTime: Int,
    var cookTime: Int,
    var difficulty: String,
    var cuisine: String,
    var mealType: String
) {


    companion object {
        const val TABLE_NAME = "Recipes"
        const val COLUMN_NAME_RECIPES = "recipes"
        const val COLUMN_NAME_IMAGE = "image"
        const val COLUMN_NAME_INGREDIENTS = "ingredients"
        const val COLUMN_NAME_INSTRUCTIONS = "instructions"
        const val COLUMN_NAME_PREP_TIME = "prep_time"
        const val COLUMN_NAME_COOK_TIME = "cook_time"
        const val COLUMN_NAME_DIFFICULTY = "difficulty"
        const val COLUMN_NAME_CUISINE = "cuisine"
        const val COLUMN_NAME_MEAL_TYPE = "mealType"


        val COLUMN_NAMES = arrayOf(
            DatabaseManager.COLUMN_NAME_ID,
            COLUMN_NAME_RECIPES,
            COLUMN_NAME_IMAGE,
            COLUMN_NAME_MEAL_TYPE,
            COLUMN_NAME_CUISINE,
            COLUMN_NAME_DIFFICULTY,
            COLUMN_NAME_COOK_TIME,
            COLUMN_NAME_INGREDIENTS,
            COLUMN_NAME_INSTRUCTIONS,
            COLUMN_NAME_PREP_TIME

        )


    }


}