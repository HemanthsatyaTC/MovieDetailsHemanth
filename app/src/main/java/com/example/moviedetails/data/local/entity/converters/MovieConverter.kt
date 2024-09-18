package com.example.moviedetails.data.local.entity.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class MovieConverter {
    // Convert List<Int> to a String for storage in the database
    @TypeConverter
    fun fromIntList(genreIds: List<Int>): String {
        return Gson().toJson(genreIds)
    }

    // Convert a String back to List<Int> when reading from the database
    @TypeConverter
    fun toIntList(genreIdsString: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(genreIdsString, listType)
    }
}