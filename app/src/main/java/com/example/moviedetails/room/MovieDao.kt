package com.example.moviedetails.room
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//interface MovieDao {
//    @Query("SELECT * FROM upcoming_movies")
//    suspend fun getAllMovies(): List<MovieEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMovies(movies: List<MovieEntity>)
//
//    @Query("DELETE FROM upcoming_movies")
//    suspend fun clearAllMovies()
//}