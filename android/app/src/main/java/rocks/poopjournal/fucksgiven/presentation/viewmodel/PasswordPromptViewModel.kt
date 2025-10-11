package rocks.poopjournal.fucksgiven.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.data.SecureStorage
import javax.inject.Inject

data class PasswordPromptUiState(
    val password: String = "",
    val passwordError: String? = null
)

@HiltViewModel()
class PasswordPromptViewModel @Inject constructor(
    secureStorage: SecureStorage
): ViewModel() {
    private val _state = MutableStateFlow(PasswordPromptUiState())
    val state = _state.asStateFlow()

    private val _authenticated = Channel<Unit>()
    val authenticated = _authenticated.receiveAsFlow()

    private val savedPassword = secureStorage.getPassword()

    fun onChangePassword(value: String){
        _state.update { it.copy(password = value) }
    }

    fun submitPassword() {
        when {
            state.value.password.isEmpty() -> {
                _state.update { it.copy(passwordError = "Password cannot be empty") }
            }

            state.value.password != savedPassword -> {
                _state.update { it.copy(passwordError = "Password is not correct") }
            }

            else -> {
                _state.update { it.copy(passwordError = null) }
                viewModelScope.launch {
                    _authenticated.send(Unit)
                }
            }
        }
    }
}