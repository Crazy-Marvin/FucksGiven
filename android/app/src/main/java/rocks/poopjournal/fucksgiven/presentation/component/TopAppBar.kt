package rocks.poopjournal.fucksgiven.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.navigation.SETTINGS_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    navigate : NavHostController
) {
    TopAppBar(title = {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ) ,
        actions = {
        IconButton(onClick = { navigate.navigate(SETTINGS_SCREEN) }) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    })
}