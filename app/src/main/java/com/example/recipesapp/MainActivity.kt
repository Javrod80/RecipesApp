package com.example.recipesapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.recipesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean { //barra
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}