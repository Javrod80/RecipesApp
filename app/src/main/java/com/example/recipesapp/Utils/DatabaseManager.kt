package com.example.recipesapp.Utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.recipesapp.Data.DataRecipes

class DatabaseManager(context: Context) : SQLiteOpenHelper (context,DATABASE_NAME,null,DATABASE_VERSION) {


    companion object {

        const val DATABASE_NAME = "listOfRecipes.db"
        const val DATABASE_VERSION = 1
        const val COLUMN_NAME_ID = "id"


        private const val SQL_CREATE_TABLE_RECIPES =
            "CREATE TABLE ${DataRecipes.TABLE_NAME} (" +
                    "$COLUMN_NAME_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${DataRecipes.COLUMN_NAME_RECIPES} TEXT ," +
                    "${DataRecipes.COLUMN_NAME_IMAGE} TEXT ," +
                    "${DataRecipes.COLUMN_NAME_INGREDIENTS} TEXT ," +
                    "${DataRecipes.COLUMN_NAME_INSTRUCTIONS} TEXT," +
                    "${DataRecipes.COLUMN_NAME_PREP_TIME} INTEGER ," +
                    "${DataRecipes.COLUMN_NAME_COOK_TIME} INTEGER ," +
                    "${DataRecipes.COLUMN_NAME_DIFFICULTY} TEXT ," +
                    "${DataRecipes.COLUMN_NAME_CUISINE} TEXT ," +
                    "${DataRecipes.COLUMN_NAME_MEAL_TYPE} TEXT )"


                    private const
                    val SQL_DELETE_TABLE_RECIPES = "DROP TABLE IF EXISTS ${DataRecipes.TABLE_NAME}"


    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_RECIPES)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        destroyDatabasse(db)
        onCreate(db)
    }

    private fun destroyDatabasse(db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE_RECIPES)


    }


}



