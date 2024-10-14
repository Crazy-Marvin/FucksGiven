package rocks.poopjournal.fucksgiven.presentation.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import rocks.poopjournal.fucksgiven.presentation.screens.AboutScreen
import rocks.poopjournal.fucksgiven.presentation.viewmodel.HomeViewModel
import rocks.poopjournal.fucksgiven.presentation.screens.HomeScreen
import rocks.poopjournal.fucksgiven.presentation.screens.PasswordPromptScreen
import rocks.poopjournal.fucksgiven.presentation.screens.SettingScreen
import rocks.poopjournal.fucksgiven.presentation.screens.StatsScreen
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import rocks.poopjournal.fucksgiven.presentation.viewmodel.SettingsViewModel
import rocks.poopjournal.fucksgiven.presentation.viewmodel.StatsViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NavGraph(navController: NavHostController,themeSetting: ThemeSetting, context: Context){
    val viewModel : HomeViewModel = hiltViewModel()
    val statsViewModel : StatsViewModel = hiltViewModel()
    val settingsViewModel : SettingsViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = HOME_SCREEN) {
        composable(route = HOME_SCREEN){
            HomeScreen(navController,viewModel)
        }
        composable(route = STATS_SCREEN){
            StatsScreen(navController = navController, viewModel = statsViewModel, themeSetting = themeSetting, context = context)
        }

        composable(route = ABOUT_SCREEN){
            AboutScreen(navController = navController)
        }

        composable(route = SETTINGS_SCREEN){
            SettingScreen(navController = navController, viewModel = settingsViewModel, context = context)
        }
        composable(route = PASSWORD_PROMPT_SCREEN){
            PasswordPromptScreen(context, onAuthenticated = {
                navController.navigate(HOME_SCREEN)
            })
        }
    }
}