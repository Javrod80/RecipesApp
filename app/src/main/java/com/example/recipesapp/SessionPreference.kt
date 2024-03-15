package com.example.recipesapp

import android.content.Context
import android.content.SharedPreferences

class SessionPreference (context: Context){


    companion object {
        const val FAVORITE_RECIPE = "FAVORITE_RECIPE"
    }

    private var sharedPref: SharedPreferences? = null

    var favoriteRecipe:String?
        get() = getFavoriteRecipeValue()
        set(value) = setFavoriteRecipeValue(value!!)

    init {
        sharedPref = context.getSharedPreferences("my_session", Context.MODE_PRIVATE)
    }

    fun setFavoriteRecipeValue (id:String) {
        val editor = sharedPref?.edit()
        if (editor != null) {
            editor.putString(FAVORITE_RECIPE, id)
            editor.apply()
        }
    }

    fun getFavoriteRecipeValue ():String? {
        return sharedPref?.getString(FAVORITE_RECIPE, null)
    }
}
