package rocks.poopjournal.fucksgiven

import android.app.LocaleConfig
import android.app.LocaleManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import rocks.poopjournal.fucksgiven.presentation.navigation.NavGraph
import rocks.poopjournal.fucksgiven.presentation.ui.theme.FucksGivenTheme
import rocks.poopjournal.fucksgiven.presentation.ui.utils.AppTheme
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var themeSetting: ThemeSetting

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val localeManager = applicationContext
                    .getSystemService(LocaleManager::class.java)
                localeManager.overrideLocaleConfig = LocaleConfig(
                    LocaleList.forLanguageTags("en-US,de,ur,fr")
                )

                val overrideLocaleConfig = localeManager.overrideLocaleConfig
                val supportedLocales = overrideLocaleConfig?.supportedLocales
            }
            val theme = themeSetting.themeFlow.collectAsState()
            val useDarkColors = when (theme.value) {
                AppTheme.LIGHT -> false
                AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                AppTheme.DARK -> true
            }
            FucksGivenTheme(darkTheme = useDarkColors) {
                NavGraph(navController = rememberNavController())
            }
        }
    }
}
