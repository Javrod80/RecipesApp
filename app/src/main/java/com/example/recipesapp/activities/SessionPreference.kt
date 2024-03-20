package com.example.recipesapp.activities

import android.content.Context
import android.content.SharedPreferences

class SessionPreference (context: Context){


    companion object {
        const val FAVORITE_RECIPE = "FAVORITE_RECIPE"
        const val DID_FETCH_DATA = "DID_FETCH_DATA"
    }

    private var sharedPref: SharedPreferences? = null

    var favoriteRecipe:String?
        get() = getFavoriteRecipeValue()
        set(value) = setFavoriteRecipeValue(value!!)

    var didFetchData:Boolean
        get() = getDidFetchDataValue()
        set(value) = setDidFetchDataValue(value!!)

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

    fun setDidFetchDataValue (done:Boolean) {
        val editor = sharedPref?.edit()
        if (editor != null) {
            editor.putBoolean(DID_FETCH_DATA, done)
            editor.apply()
        }
    }

    fun getDidFetchDataValue ():Boolean {
        return sharedPref?.getBoolean(DID_FETCH_DATA, false) ?: false
    }
}
