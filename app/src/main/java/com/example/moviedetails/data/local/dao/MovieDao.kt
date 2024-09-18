package com.example.moviedetails.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedetails.data.local.entity.UpcomingMovieEntity
@Dao
interface MovieDao {

    @Query("SELECT * FROM upcoming_movies")
    suspend fun getAllMovies(): List<UpcomingMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<UpcomingMovieEntity>)

    @Query("DELETE FROM upcoming_movies")
    suspend fun clearMovies()

    //    get movie details with id
    @Query("SELECT * FROM upcoming_movies WHERE id = :id")
    suspend fun getMovieById(id: Int): UpcomingMovieEntity
}