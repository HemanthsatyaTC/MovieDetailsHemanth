package com.example.moviedetails.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedetails.data.model.upcoming.ResultUpcomingModel
import com.example.moviedetails.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _upcomingData = mutableStateOf<List<ResultUpcomingModel>>(emptyList())
    val upcomingData: State<List<ResultUpcomingModel>> = _upcomingData

    init {
        fetchDetails()
        
    }

    private fun fetchDetails() {
        viewModelScope.launch {
            try {
                val details = repository.getUpcoming(apiKey = "757e85be4b3665dab13c9c7588bb99c0")
                _upcomingData.value = details.results as List<ResultUpcomingModel>
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching data: ${e.message}")
            }
        }
    }
}