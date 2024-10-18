package rocks.poopjournal.fucksgiven

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import rocks.poopjournal.fucksgiven.data.getPasswordProtectionEnabled
import rocks.poopjournal.fucksgiven.presentation.component.BiometricPromptManager
import rocks.poopjournal.fucksgiven.presentation.navigation.NavGraph
import rocks.poopjournal.fucksgiven.presentation.screens.PasswordPromptScreen
import rocks.poopjournal.fucksgiven.presentation.ui.theme.FucksGivenTheme
import rocks.poopjournal.fucksgiven.presentation.ui.utils.AppTheme
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var themeSetting: ThemeSetting

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme = themeSetting.themeFlow.collectAsState()
            val useDarkColors = when (theme.value) {
                AppTheme.LIGHT -> false
                AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
                AppTheme.DARK -> true
            }
            FucksGivenTheme(darkTheme = useDarkColors) {
                var isAuthenticated by remember { mutableStateOf(false) }
                val isPasswordProtectionEnabled = getPasswordProtectionEnabled(context = this)
                if (isAuthenticated || !isPasswordProtectionEnabled) {
                    NavGraph(navController = rememberNavController(), themeSetting = themeSetting, context = this)
                } else {
                    PasswordPromptScreen(context = this) {
                        isAuthenticated = true
                    }
                }
            }
        }
    }
}
