package rocks.poopjournal.fucksgiven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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
    @OptIn(ExperimentalPermissionsApi::class)
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
                NavGraph(navController = rememberNavController())
            }
        }
    }
}
