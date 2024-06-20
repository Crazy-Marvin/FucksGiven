package rocks.poopjournal.fucksgiven.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import rocks.poopjournal.fucksgiven.presentation.viewmodel.HomeViewModel
import rocks.poopjournal.fucksgiven.presentation.screens.HomeScreen
import rocks.poopjournal.fucksgiven.presentation.screens.SettingScreen
import rocks.poopjournal.fucksgiven.presentation.screens.StatsScreen
import rocks.poopjournal.fucksgiven.presentation.viewmodel.SettingsViewModel
import rocks.poopjournal.fucksgiven.presentation.viewmodel.StatsViewModel

@Composable
fun NavGraph(navController: NavHostController){
    val viewModel : HomeViewModel = hiltViewModel()
    val statsViewModel : StatsViewModel = hiltViewModel()
    val settingsViewModel : SettingsViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = HOME_SCREEN) {
        composable(route = HOME_SCREEN){
            HomeScreen(navController,viewModel)
        }
        composable(route = STATS_SCREEN){
            StatsScreen(navController = navController, viewModel = statsViewModel)
        }

        composable(route = ABOUT_SCREEN){

        }

        composable(route = SETTINGS_SCREEN){
            SettingScreen(navController = navController, viewModel = settingsViewModel)
        }
    }
}