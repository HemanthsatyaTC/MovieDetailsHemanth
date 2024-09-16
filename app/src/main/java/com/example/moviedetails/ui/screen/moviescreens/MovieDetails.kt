package com.example.moviedetails.ui.screen.moviescreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviedetails.viewmodel.UpcomingViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.moviedetails.data.model.upcoming.ResultUpcomingModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun MovieDetails(
    name: String,
    viewModel: UpcomingViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val upcomingData by viewModel.upcomingData
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Welcome to the Movies,, $name") },
                    backgroundColor = Color.Cyan,
                    contentColor = Color.White,
//                    modifier = Modifier.(10.dp)

                )
            }
        ) {
            Spacer(modifier = Modifier.height(205.dp))
            if (upcomingData.isEmpty()) {
                // Show loading message or animation
                Text(text = "Loading...")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(upcomingData) { movies ->
                        UpcomingMovies(movies = movies, navController = navController
                            )

                    }
                }
            }

        }
}


@Composable
fun UpcomingMovies(movies: ResultUpcomingModel, navController: NavHostController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = {
                navController.navigate("movie_detail/${movies.title}")
            })
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