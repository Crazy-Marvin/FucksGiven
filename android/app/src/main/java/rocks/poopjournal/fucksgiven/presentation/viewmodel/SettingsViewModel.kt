package rocks.poopjournal.fucksgiven.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.data.DatabaseBackupManager
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val databaseBackupManager: DatabaseBackupManager
) : ViewModel() {

    @Inject
    lateinit var themeSetting: ThemeSetting


    fun backupDatabase(message : String) {
        viewModelScope.launch {
            val result = databaseBackupManager.backupDatabase(message)
            // Handle the result
        }
    }

    fun restoreDatabase() {
        viewModelScope.launch {
            databaseBackupManager.restoreDatabase()
        }
    }

}