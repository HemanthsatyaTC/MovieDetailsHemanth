package com.example.moviedetails.data.repository

import android.util.Log
import com.example.moviedetails.data.local.dao.MovieDao
//import com.example.moviedetails.data.local.dao.MovieDao
//import com.example.moviedetails.data.local.entity.toMovieModel
import com.example.moviedetails.data.model.popular.MoviesDataModel
import com.example.moviedetails.data.model.show.PresentDataModel
import com.example.moviedetails.data.model.upcoming.ResultUpcomingModel
import com.example.moviedetails.data.model.upcoming.UpcomingDataModel
import com.example.moviedetails.data.model.upcoming.toMovieEntity
import com.example.moviedetails.data.model.upcoming.toMovieModel
import com.example.moviedetails.data.remote.popular.PopularApiInterface
import com.example.moviedetails.data.remote.show.ShowApiInterface
import com.example.moviedetails.data.remote.upcoming.UpcomingApiInterface
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    val apiUpcoming: UpcomingApiInterface,
    val apiShow: ShowApiInterface,
    val apiPopular: PopularApiInterface,
    val movieDao: MovieDao,
): Repository {
    override suspend fun getUpcoming(apiKey: String): UpcomingDataModel {
        val response = apiUpcoming.getUpcoming("757e85be4b3665dab13c9c7588bb99c0")
        movieDao.clearMovies()
        response.results?.let { movies ->
            val movieEntities = movies.filterNotNull().map { it.toMovieEntity() }
            movieDao.insertAll(movieEntities)
        }
        return response
    }

    override suspend fun getPopular(apiKey: String): MoviesDataModel {
        return apiPopular.getPopular("757e85be4b3665dab13c9c7588bb99c0")
    }

    override suspend fun getShows(apiKey: String): PresentDataModel {
        return apiShow.getShows("757e85be4b3665dab13c9c7588bb99c0")
    }

    override suspend fun movieDao(): MovieDao {
        TODO("Not yet implemented")
    }

    override suspend fun getMoviesFromRoomAndAPI(): List<ResultUpcomingModel?> {
        val localMovies = movieDao.getAllMovies()
        return if (localMovies.isNotEmpty()) {
            Log.i("DEBUG_TAG -> Repository", "Fetching data from Room")
            // If Room has data, return it (convert MovieEntity to MovieModel)
            localMovies.map { it.toMovieModel() }
        } else {
            try {
                val response = apiUpcoming.getUpcoming("757e85be4b3665dab13c9c7588bb99c0")
                val movies = response.results ?: emptyList()

                // Cache the movies in Room
                movieDao.clearMovies()  // Clear old data if needed
                if (movies.isNotEmpty()) {
                    movieDao.insertAll(movies.map { it?.toMovieEntity() ?: ResultUpcomingModel() .toMovieEntity()})  // Insert non-null list
                }  // Convert MovieModel to MovieEntity and insert

                Log.i("DEBUG_TAG -> Repository", "Fetching data from API")
                // Return the fetched movies
                movies
            } catch (e: Exception) {
                // If the API call fails, return an empty list or handle the error accordingly
                Log.e("DEBUG_TAG -> Repository", "Error fetching data: ${e.message}")
                emptyList()
            }
        }
    }
//
}