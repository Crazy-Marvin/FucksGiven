package rocks.poopjournal.fucksgiven.presentation.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.data.getPasswordProtectionEnabled
import rocks.poopjournal.fucksgiven.data.savePassword
import rocks.poopjournal.fucksgiven.data.setPasswordProtectionEnabled
import rocks.poopjournal.fucksgiven.presentation.component.ThemeContent
import rocks.poopjournal.fucksgiven.presentation.navigation.ABOUT_SCREEN
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import rocks.poopjournal.fucksgiven.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController, viewModel: SettingsViewModel, context: Context) {
    var showDialog by remember { mutableStateOf(false) }
    val toastMessage = stringResource(id = R.string.backup_success)
    var isPasswordProtectionEnabled by remember { mutableStateOf(getPasswordProtectionEnabled(context)) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings), style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
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
                        text = stringResource(id = R.string.general),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 11.dp)
                    )
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(11.dp)
                    .clickable { showDialog = true }) {
                    Text(text = stringResource(id = R.string.apperance), style = MaterialTheme.typography.bodyLarge)
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
                        text = stringResource(R.string.security),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 11.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 11.dp)
                ) {
                    Text(text = stringResource(R.string.enable_app_protection))
                    Switch(
                        modifier = Modifier.padding(start = 4.dp),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                            uncheckedTrackColor = Color.White,
                        ),
                        checked = isPasswordProtectionEnabled,
                        onCheckedChange = { enabled ->
                            isPasswordProtectionEnabled = enabled

                            if (enabled) {
                                showPasswordDialog = true
                            } else{
                                isPasswordProtectionEnabled = false
                                setPasswordProtectionEnabled(context, false)
                            }
                        }
                    )
                }
            }
            if (showPasswordDialog) {
                SetPasswordScreen(
                    context = context,
                    onPasswordSet = {
                        showPasswordDialog = false
                    },
                    onDismissRequest = {
                        showPasswordDialog = false
                        isPasswordProtectionEnabled = false
                        setPasswordProtectionEnabled(context, false)
                    }
                )
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
                        text = stringResource(id = R.string.data),
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
                            viewModel.backupDatabase(toastMessage)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.backup),
                        contentDescription = stringResource(id = R.string.backup)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.backup), style = MaterialTheme.typography.bodyLarge)
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
                        contentDescription = stringResource(id = R.string.restore)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.restore), style = MaterialTheme.typography.bodyLarge)
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
                        text = stringResource(id = R.string.about),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(ABOUT_SCREEN)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.about),
                        contentDescription = "about"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.about), style = MaterialTheme.typography.bodyLarge)
                }
            }
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
        title = { Text(text = stringResource(id = R.string.select_theme)) },
        text = {
            ThemeContent(selectedTheme = theme.value, onItemSelect = { themes ->
                userSetting.theme = themes
            })
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.ok), color = Color.White)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPasswordScreen(context: Context, onPasswordSet: () -> Unit, onDismissRequest: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismissRequest){
    Column(
        modifier = Modifier.clipToBounds(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter Password") },
            modifier = Modifier.padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
            if (password == confirmPassword && password.isNotBlank()) {
                setPasswordProtectionEnabled(context, true)
                savePassword(context, password)
                onPasswordSet()
            } else {
                password = ""
                confirmPassword = ""
                Toast.makeText(context, "Password didn't match", Toast.LENGTH_SHORT ).show()
            }
        }) {
            Text(text = "Set Password", color = Color.White)
        }
        }
    }
}
