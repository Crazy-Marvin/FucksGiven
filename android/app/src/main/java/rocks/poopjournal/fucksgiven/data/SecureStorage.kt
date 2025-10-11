package rocks.poopjournal.fucksgiven.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureStorage @Inject constructor(@ApplicationContext private val context: Context) {

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = getEncryptedSharedPreferences(context)
    }

    private val _passwordProtectionEnabledFlow = MutableStateFlow(getPasswordProtectionEnabled())
    val passwordProtectionEnabledFlow = _passwordProtectionEnabledFlow.asStateFlow()

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            "secure_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun savePassword(password: String) {
        _passwordProtectionEnabledFlow.update { true }
        sharedPreferences.edit(commit = true) {
            putString("user_password", password)
            putBoolean("password_protection_enabled", true)
        }
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("user_password", null)
    }

    fun clearStoredPassword() {
        _passwordProtectionEnabledFlow.update { false }
        sharedPreferences.edit(commit = true) {
            remove("user_password")
            remove("password_protection_enabled")
        }
    }

    fun getPasswordProtectionEnabled(): Boolean {
        return sharedPreferences.getBoolean("password_protection_enabled", false)
    }
}