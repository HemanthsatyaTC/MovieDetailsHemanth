package com.example.moviedetails.data.repository

import com.example.moviedetails.data.model.popular.MoviesDataModel
import com.example.moviedetails.data.model.popular.ResultModel
import com.example.moviedetails.data.model.show.PresentDataModel
import com.example.moviedetails.data.model.show.ResultShowModel
import com.example.moviedetails.data.model.upcoming.ResultUpcomingModel
import com.example.moviedetails.data.model.upcoming.UpcomingDataModel
import com.example.moviedetails.data.remote.popular.PopularApiInterface
import com.example.moviedetails.data.remote.show.ShowApiInterface
import com.example.moviedetails.data.remote.upcoming.UpcomingApiInterface
import com.example.moviedetails.room.MovieDao
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    val apiUpcoming: UpcomingApiInterface,
    val apiShow: ShowApiInterface,
    val apiPopular: PopularApiInterface,
    private val movieDao: MovieDao
): Repository {
    override suspend fun getUpcoming(apiKey: String): UpcomingDataModel {
        return apiUpcoming.getUpcoming("757e85be4b3665dab13c9c7588bb99c0")
    }

    override suspend fun getPopular(apiKey: String): MoviesDataModel {
        return apiPopular.getPopular("757e85be4b3665dab13c9c7588bb99c0")
    }

    override suspend fun getShows(apiKey: String): PresentDataModel {
        return apiShow.getShows("757e85be4b3665dab13c9c7588bb99c0")
    }
}