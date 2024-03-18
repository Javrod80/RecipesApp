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
            val taskName = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_RECIPES))



            dataRecipes = DataRecipes(id, taskName)


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
            val taskName = cursor.getString(cursor.getColumnIndex(DataRecipes.COLUMN_NAME_RECIPES))


            val dataRecipes: DataRecipes = DataRecipes(id, taskName)

            list.add(dataRecipes)

        }
        cursor.close()
        db.close()

        return list


    }
}
