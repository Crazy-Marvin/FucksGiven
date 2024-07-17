package rocks.poopjournal.fucksgiven.presentation.widget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.data.FuckRepository
import rocks.poopjournal.fucksgiven.presentation.viewmodel.UiState
import javax.inject.Inject

class AppWidgetViewModel @Inject constructor(
    private val fuckRepository: FuckRepository,
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
}