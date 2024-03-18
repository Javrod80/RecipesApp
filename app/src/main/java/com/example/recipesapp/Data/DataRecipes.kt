package com.example.recipesapp.Data

import com.example.recipesapp.Utils.DatabaseManager

class DataRecipes (var id : Int , var recipe : String, var image : String) {


    companion object {
        const val TABLE_NAME = "Recipes"
        const val COLUMN_NAME_RECIPES = "recipes"
        const val COLUMN_NAME_IMAGE = "image"
        val  COLUMN_NAMES = arrayOf(
            DatabaseManager.COLUMN_NAME_ID,
            COLUMN_NAME_RECIPES,
            COLUMN_NAME_IMAGE

        )


    }






}