package rocks.poopjournal.fucksgiven.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    return EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun savePassword(context: Context, password: String) {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putString("user_password", password)
    editor.apply()
}

fun getPassword(context: Context): String? {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    return sharedPreferences.getString("user_password", null)
}

fun clearStoredPassword(context: Context) {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.remove("user_password")
    editor.apply()
}

fun setPasswordProtectionEnabled(context: Context, enabled: Boolean) {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putBoolean("password_protection_enabled", enabled)
    editor.apply()
}

fun getPasswordProtectionEnabled(context: Context): Boolean {
    val sharedPreferences = getEncryptedSharedPreferences(context)
    return sharedPreferences.getBoolean("password_protection_enabled", false)
}
