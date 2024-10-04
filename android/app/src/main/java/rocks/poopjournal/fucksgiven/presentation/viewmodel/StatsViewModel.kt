package rocks.poopjournal.fucksgiven.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.data.FuckRepository
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(private val repository: FuckRepository) : ViewModel() {


    val weeklyData: LiveData<List<FuckData>> = repository.getWeeklyData().map {
        it ?: emptyList() // Handle null by returning an empty list
    }

    val monthlyData: LiveData<List<FuckData>> = repository.getMonthlyData().map {
        it ?: emptyList() // Handle null by returning an empty list
    }

    val yearlyData: LiveData<List<FuckData>> = repository.getYearlyData().map {
        it ?: emptyList() // Handle null by returning an empty list
    }

}