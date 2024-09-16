package com.example.moviedetails.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedetails.data.model.popular.ResultModel
import com.example.moviedetails.data.model.show.ResultShowModel
import com.example.moviedetails.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularShowModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _showData = mutableStateOf<List<ResultModel>>(emptyList())
    val showData: State<List<ResultModel>> = _showData

    init {
        fetchDetails()

    }

    private fun fetchDetails() {
        viewModelScope.launch {
            try {
                val details = repository.getPopular(apiKey = "757e85be4b3665dab13c9c7588bb99c0")
                _showData.value = details.results as List<ResultModel>
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}")
            }
        }
    }
}