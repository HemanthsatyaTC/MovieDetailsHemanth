package com.example.moviedetails.data.model.show


import com.google.gson.annotations.SerializedName

data class PresentDataModel(
    @SerializedName("dates")
    val dates: DatesModel? = DatesModel(),
    @SerializedName("page")
    val page: Int? = 0,
    @SerializedName("results")
    val results: List<ResultShowModel?>? = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)