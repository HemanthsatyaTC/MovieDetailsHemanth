package com.example.moviedetails.data.remote.show

import com.example.moviedetails.data.model.show.PresentDataModel
import com.example.moviedetails.data.model.show.ResultShowModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ShowApiInterface {
    @GET(ShowApiDetails.END_POINTS)
    suspend fun getShows(
        @Query("api_key") apiKey: String = "757e85be4b3665dab13c9c7588bb99c0"
    ): PresentDataModel
}