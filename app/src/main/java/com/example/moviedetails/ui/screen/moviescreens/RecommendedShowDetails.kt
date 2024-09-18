package com.example.moviedetails.ui.screen.moviescreens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.moviedetails.data.model.popular.ResultModel
import com.example.moviedetails.ui.screen.loginpages.logout
import com.example.moviedetails.viewmodel.PopularShowModel
import dagger.hilt.android.lifecycle.HiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecommendedShowDetails(
    viewModel: PopularShowModel = hiltViewModel(),
    navController: NavHostController
) {
    val showData by viewModel.showData
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recommended / Popular Show Details") },
                backgroundColor = Color.Cyan,
                contentColor = Color.White,
                actions = {
                    // Add a Logout button
                    IconButton(onClick = {
                        logout(navController = navController, context)  // Call the logout function on click
                    }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.Black)
                    }
                }
            )
        }
    ) {
        Spacer(modifier = Modifier.height(205.dp))
        if (showData.isEmpty()) {
            // Show loading message or animation
            Text(text = "Loading...")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(showData) { movies ->
                    ShowPopularMovies(movies = movies)

                }
            }
        }
    }

}

@Composable
fun ShowPopularMovies(movies: ResultModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ){
        Column (modifier = Modifier.padding(16.dp)) {
            movies.posterPath?.let { posterPath ->
                Image(
                    painter = rememberImagePainter("https://image.tmdb.org/t/p/w500$posterPath"),
                    contentDescription = movies.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                )
            }

            Text(
                text = "${movies.title}",
                style = MaterialTheme.typography.h3
            )

            Text(
                text = "Release Date: ${movies.releaseDate}",
                style = MaterialTheme.typography.body1
            )

        }
    }


}
