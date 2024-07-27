package rocks.poopjournal.fucksgiven.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.data.FuckRepository
import javax.inject.Inject

data class UiState(
    val fuckList: List<FuckData> = listOf(),
    val currentlyViewedTask: FuckData? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fuckRepository: FuckRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fuckRepository.getAllFucks().distinctUntilChanged().collect { list ->
                _uiState.update {
                    it.copy(
                        fuckList = list
                    )
                }
            }
        }
    }


    fun getFuck(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fuckRepository.getFuck(id).distinctUntilChanged().collect { task ->
                _uiState.update {
                    it.copy(
                        currentlyViewedTask = task
                    )
                }
            }
        }
    }

    fun addFuck(fuck: FuckData,context: Context) {
        if(fuck.description.isEmpty() && fuck.date == 0L){
            Toast.makeText(context,"Fill all information",Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch {
            fuckRepository.insertFuck(fuck)
        }
    }


}