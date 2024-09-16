package com.example.moviedetails.data.remote.popular

import com.example.moviedetails.data.model.popular.MoviesDataModel
import com.example.moviedetails.data.model.popular.ResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularApiInterface {
    @GET(PopularApiDetails.END_POINTS)
    suspend fun getPopular(
        @Query("api_key") apiKey: String = "757e85be4b3665dab13c9c7588bb99c0"
    ): MoviesDataModel
}