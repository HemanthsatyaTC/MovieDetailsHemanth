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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.rememberImagePainter
import com.example.moviedetails.data.model.popular.ResultModel
import com.example.moviedetails.data.model.show.ResultShowModel
import com.example.moviedetails.viewmodel.ShowViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowDetails(
    viewModel: ShowViewModel = hiltViewModel()
) {

    val showData by viewModel.showData
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Show Details ") },
                backgroundColor = Color.Cyan,
                contentColor = Color.White
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
                    ShowMovies(movies = movies)

                }
            }
        }
    }
}



@Composable
fun ShowMovies(movies: ResultShowModel) {
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