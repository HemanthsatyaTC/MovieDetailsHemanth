package com.example.moviedetails.data.remote.upcoming

import com.example.moviedetails.data.model.upcoming.ResultUpcomingModel
import com.example.moviedetails.data.model.upcoming.UpcomingDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface UpcomingApiInterface {
    @GET(UpcomingApiDetails.END_POINTS)
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String = "757e85be4b3665dab13c9c7588bb99c0"
    ): UpcomingDataModel
}