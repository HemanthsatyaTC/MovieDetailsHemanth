package com.example.moviedetails.data.repository

//import com.example.moviedetails.data.local.dao.MovieDao
import com.example.moviedetails.data.local.dao.MovieDao
import com.example.moviedetails.data.model.popular.MoviesDataModel
import com.example.moviedetails.data.model.show.PresentDataModel
import com.example.moviedetails.data.model.show.ResultShowModel
import com.example.moviedetails.data.model.upcoming.ResultUpcomingModel
import com.example.moviedetails.data.model.upcoming.UpcomingDataModel

interface Repository {
    suspend fun getUpcoming(apiKey:String): UpcomingDataModel
    suspend fun getPopular(apiKey:String): MoviesDataModel
    suspend fun getShows(apiKey:String): PresentDataModel
    suspend fun movieDao(): MovieDao
    suspend fun getMoviesFromRoomAndAPI() : List<ResultUpcomingModel?>
}