package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pokemonapp.components.PokeAPIApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            PokeAPITheme {
                // A surface container using the 'background' color from the theme
                PokeAPIApp()
//            }
        }
    }
}