package rocks.poopjournal.fucksgiven.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.navigation.HOME_SCREEN
import rocks.poopjournal.fucksgiven.presentation.navigation.STATS_SCREEN
import rocks.poopjournal.fucksgiven.presentation.ui.theme.FuckGreen

data class BottomNavigationItem(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String,
)


object BottomBar {
    fun getMenuBottomItems() = mutableListOf<BottomNavigationItem>(
        BottomNavigationItem(
            selectedIcon = R.drawable.selected_home,
            unselectedIcon = R.drawable.home,
            route = HOME_SCREEN
        ),
        BottomNavigationItem(
            selectedIcon = R.drawable.selected_graph,
            unselectedIcon = R.drawable.graph,
            route = STATS_SCREEN
        ),
    )
}

@Composable
fun BottomNavBar(
    navHostController: NavHostController,
    items: List<BottomNavigationItem>
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        items.forEachIndexed { index, bottomNavigationItem ->
            val isSelected = currentRoute == bottomNavigationItem.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navHostController.navigate(bottomNavigationItem.route) {
                            navHostController.graph.startDestinationRoute?.let { screenRoute ->
                                popUpTo(screenRoute) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = Color.Gray,
                    selectedIconColor = FuckGreen,
                ),
                icon = {
                    Icon(
                        painter = painterResource(id = if (isSelected) bottomNavigationItem.selectedIcon else bottomNavigationItem.unselectedIcon
                        ),
                        contentDescription = bottomNavigationItem.route
                    )
                },
            )
        }
    }
}
