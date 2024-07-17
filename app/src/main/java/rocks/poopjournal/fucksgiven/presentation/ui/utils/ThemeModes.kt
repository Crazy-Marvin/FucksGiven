package rocks.poopjournal.fucksgiven.presentation.ui.utils

import kotlinx.coroutines.flow.StateFlow

enum class AppTheme(val nameTheme : String){
    LIGHT("Light"),
    DARK( "Dark"),
    FOLLOW_SYSTEM( "Follow System");

    companion object{
        fun fromOrdinal(ordinal : Int) = entries[ordinal]
    }
}


interface ThemeSetting {
    val themeFlow : StateFlow<AppTheme>
    var theme : AppTheme
}