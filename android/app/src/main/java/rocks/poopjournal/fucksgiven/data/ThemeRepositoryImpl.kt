package rocks.poopjournal.fucksgiven.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import rocks.poopjournal.fucksgiven.presentation.ui.utils.AppTheme
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThemeSettingImpl @Inject constructor(
    @ApplicationContext context: Context,
) : ThemeSetting {
    override var theme: AppTheme by AppThemePreferenceDelegate("app_theme", AppTheme.LIGHT)
    override val themeFlow: MutableStateFlow<AppTheme>
    private val preferences: SharedPreferences =
        context.getSharedPreferences("sample_theme", Context.MODE_PRIVATE)
    init {
        themeFlow = MutableStateFlow(theme)
    }
    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: AppTheme,
    ) : ReadWriteProperty<Any, AppTheme> {
        override fun getValue(thisRef: Any, property: KProperty<*>): AppTheme =
            AppTheme.fromOrdinal(preferences.getInt(name, default.ordinal))

        override fun setValue(thisRef: Any, property: KProperty<*>, value: AppTheme) {
            themeFlow.value = value
            preferences.edit {
                putInt(name,value.ordinal)
            }
        }
    }

}