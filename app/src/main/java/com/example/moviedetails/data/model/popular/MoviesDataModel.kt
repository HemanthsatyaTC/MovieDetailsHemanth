package com.example.moviedetails.data.model.popular


import com.google.gson.annotations.SerializedName

data class MoviesDataModel(
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<ResultModel?>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)