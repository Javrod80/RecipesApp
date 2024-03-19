package com.example.recipesapp.Provider

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.recipesapp.Data.DataRecipes
import com.example.recipesapp.Utils.DatabaseManager

class RecipeDAO (context: Context) {

    private var databaseManager: DatabaseManager = DatabaseManager(context)


    fun insert(dataRecipes: DataRecipes): DataRecipes {

        val db = databaseManager.writableDatabase

        var values = ContentValues()
        values.put(DataRecipes.COLUMN_NAME_RECIPES, dataRecipes.recipe)
        values.put(DataRecipes.COLUMN_NAME_IMAGE,dataRecipes.image)
        values.put(DataRecipes.COLUMN_NAME_MEAL_TYPE,dataRecipes.mealType)
        values.put(DataRecipes.COLUMN_NAME_CUISINE,dataRecipes.cuisine)
        values.put(DataRecipes.COLUMN_NAME_COOK_TIME,dataRecipes.cookTime)
        values.put(DataRecipes.COLUMN_NAME_DIFFICULTY,dataRecipes.difficulty)
        values.put(DataRecipes.COLUMN_NAME_INGREDIENTS,dataRecipes.ingredients.joinToString (","))
        values.put(DataRecipes.COLUMN_NAME_INSTRUCTIONS,dataRecipes.instruction.joinToString (","))
        values.put(DataRecipes.COLUMN_NAME_PREP_TIME,dataRecipes.prepTime)


        var newRowId = db.insert(DataRecipes.TABLE_NAME, null, values)
        Log.i("DATABASE", "New record id: $newRowId")

        db.close()

        dataRecipes.id = newRowId.toInt()
        return dataRecipes

    }

    fun update(dataRecipes: DataRecipes) {
        val db = databaseManager.writableDatabase

        var values = ContentValues()

        values.put(DataRecipes.COLUMN_NAME_RECIPES, dataRecipes.recipe)
        values.put(DataRecipes.COLUMN_NAME_IMAGE,dataRecipes.image)
        values.put(DataRecipes.COLUMN_NAME_MEAL_TYPE,dataRecipes.mealType)
        values.put(DataRecipes.COLUMN_NAME_CUISINE,dataRecipes.cuisine)
        values.put(DataRecipes.COLUMN_NAME_COOK_TIME,dataRecipes.cookTime)
        values.put(DataRecipes.COLUMN_NAME_DIFFICULTY,dataRecipes.difficulty)
        values.put(DataRecipes.COLUMN_NAME_INGREDIENTS,dataRecipes.ingredients.joinToString (","))
        values.put(DataRecipes.COLUMN_NAME_INSTRUCTIONS,dataRecipes.instruction.joinToString (","))
        values.put(DataRecipes.COLUMN_NAME_PREP_TIME,dataRecipes.prepTime)

        var updatedRows = db.update(
            DataRecipes.TABLE_NAME,
            values,
            "${DatabaseManager.COLUMN_NAME_ID} = ${dataRecipes.id}",
            null
        )
        Log.i("DATABASE", "Updated records: $updatedRows")

        db.close()

    }

    fun delete(dataRecipes: DataRecipes) {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(
            DataRecipes.TABLE_NAME,
            "${DatabaseManager.COLUMN_NAME_ID} = ${dataRecipes.id}",
            null
        )
        Log.i("DATABASE", "Deleted rows : $deletedRows")

        db.close()

    }


    @SuppressLint("Range")
    fun find(id: Int): DataRecipes? {

        val db = databaseManager.writableDatabase

        val cursor = db.query(
            DataRecipes.TABLE_NAME,
            DataRecipes.COLUMN_NAMES,
            "${DatabaseManager.COLUMN_NAME_ID} = $id",
            null,
            null,
            null,
            null
        )

        var dataRecipes: DataRecipes? = null


        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val recipeName = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_RECIPES))
            val image = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_IMAGE))
            val mealType : String = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_MEAL_TYPE))
            val cuisine : String = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_CUISINE))
            val timeCook : Int = cursor.getInt(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_COOK_TIME))
            val prepTime : Int = cursor.getInt(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_PREP_TIME))
            val difficulty : String = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_DIFFICULTY))
            val ingredients : List<String> = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_INGREDIENTS)).split(",")
            val instruction : List<String> = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_INSTRUCTIONS)).split(",")




            dataRecipes = DataRecipes(id, recipeName,image,ingredients, instruction,prepTime,timeCook,difficulty,cuisine,mealType)


        }
        cursor.close()
        db.close()

        return dataRecipes

    }

    @SuppressLint("Range")
    fun findAll(): List<DataRecipes> {
        val db = databaseManager.writableDatabase
        val cursor = db.query(
            DataRecipes.TABLE_NAME,
            DataRecipes.COLUMN_NAMES,
            null,
            null,
            null,
            null,
            null
        )

        var list: MutableList<DataRecipes> = mutableListOf()


        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseManager.COLUMN_NAME_ID))
            val recipeName = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_RECIPES))
            val image = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_IMAGE))
            val mealType : String = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_MEAL_TYPE))
            val cuisine : String = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_CUISINE))
            val timeCook : Int = cursor.getInt(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_COOK_TIME))
            val prepTime : Int = cursor.getInt(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_PREP_TIME))
            val difficulty : String = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_DIFFICULTY))
            val ingredients : List<String> = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_INGREDIENTS)).split(",")
            val instruction : List<String> = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_INSTRUCTIONS)).split(",")

            val dataRecipes: DataRecipes = DataRecipes(id, recipeName,image,ingredients, instruction,prepTime,timeCook,difficulty,cuisine,mealType)
            list.add(dataRecipes)

        }
        cursor.close()
        db.close()

        return list


    }
}
