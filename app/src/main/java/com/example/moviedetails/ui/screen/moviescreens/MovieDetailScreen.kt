package com.example.moviedetails.ui.screen.moviescreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(navController: NavHostController, movieId: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Welcome to the Movies") },
                backgroundColor = Color.Cyan,
                contentColor = Color.White,
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Movie ID: $movieId", // Fetch and display movie details using the ID
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            // You can now display additional movie details here, like overview, release date, etc.
            Text(text = movieId)
        }
    }

}