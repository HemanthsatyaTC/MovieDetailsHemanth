package com.example.moviedetails.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviedetails.data.local.dao.MovieDao
import com.example.moviedetails.data.local.entity.UpcomingMovieEntity
import com.example.moviedetails.data.local.entity.converters.MovieConverter

@Database(entities = [UpcomingMovieEntity::class], version = 5, exportSchema = true)
//@TypeConverters(MovieConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}