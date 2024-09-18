package com.example.moviedetails.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviedetails.data.model.upcoming.ResultUpcomingModel
import com.example.moviedetails.data.model.upcoming.UpcomingDataModel

@Entity(tableName = "upcoming_movies")
data class UpcomingMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double
)

fun UpcomingMovieEntity.toMovieModel(): ResultUpcomingModel {

    return ResultUpcomingModel(
        title = this.title,
        id = this.id,
        posterPath = this.posterPath,
        overview = this.overview,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
    )
}
