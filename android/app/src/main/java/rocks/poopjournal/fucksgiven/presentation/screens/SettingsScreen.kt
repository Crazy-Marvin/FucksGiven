package rocks.poopjournal.fucksgiven.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.component.ThemeContent
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import rocks.poopjournal.fucksgiven.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController, viewModel: SettingsViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "General",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 11.dp)
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(11.dp)
                    .clickable { showDialog = true }) {
                    Text(text = "Appearance", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = viewModel.themeSetting.theme.nameTheme,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Light
                    )
                    if (showDialog) {
                        ThemeSelectionDialog(
                            onDismissRequest = { showDialog = false },
                            userSetting = viewModel.themeSetting
                        )
                    }
                }
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Data",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 11.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(11.dp)
                        .clickable {
                            viewModel.backupDatabase()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.backup),
                        contentDescription = "backup"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Backup", style = MaterialTheme.typography.bodyLarge)
                }
                Divider(
                    thickness = 0.8.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 11.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(11.dp)
                        .clickable {
                            viewModel.restoreDatabase()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.restore),
                        contentDescription = "restore"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Restore", style = MaterialTheme.typography.bodyLarge)
                }
            }
//            Column {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .background(MaterialTheme.colorScheme.secondary),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "About",
//                        color = MaterialTheme.colorScheme.primary,
//                        style = MaterialTheme.typography.labelSmall,
//                        modifier = Modifier.padding(start = 8.dp)
//                    )
//                }
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .padding(8.dp)
//                        .clickable {
//                            navController.navigate(ABOUT_SCREEN)
//                        },
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.about),
//                        contentDescription = "backup"
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(text = "About", style = MaterialTheme.typography.bodyLarge)
//                }
//            }
        }
    }
}


@Composable
fun ThemeSelectionDialog(
    onDismissRequest: () -> Unit,
    userSetting: ThemeSetting
) {
    val theme = userSetting.themeFlow.collectAsState()
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Select Theme") },
        text = {
            ThemeContent(selectedTheme = theme.value, onItemSelect = { themes ->
                userSetting.theme = themes
            })
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text("OK")
            }
        }
    )
}
