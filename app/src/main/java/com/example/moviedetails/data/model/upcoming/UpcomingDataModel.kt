package com.example.moviedetails.data.model.upcoming


import com.google.gson.annotations.SerializedName

data class UpcomingDataModel(
    @SerializedName("dates")
    val dates: DatesModel? = DatesModel(),
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<ResultUpcomingModel?>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)