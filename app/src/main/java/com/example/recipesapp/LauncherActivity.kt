package com.example.recipesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipesapp.databinding.ActivityLauncherBinding

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)



        listener ()


    }


    private fun listener () {
        binding.Boton1.setOnClickListener() {

            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }





    }
}