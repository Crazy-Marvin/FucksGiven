package rocks.poopjournal.fucksgiven.presentation.screens

import android.content.Context
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.updateAll
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.component.ThemeContent
import rocks.poopjournal.fucksgiven.presentation.navigation.ABOUT_SCREEN
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import rocks.poopjournal.fucksgiven.presentation.viewmodel.SettingsViewModel
import rocks.poopjournal.fucksgiven.presentation.widget.MyAppWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel,
    context: Context
) {
    var showDialog by remember { mutableStateOf(false) }
    val toastMessage = stringResource(id = R.string.backup_success)
    var isPasswordProtectionEnabled by remember {
        mutableStateOf(
            viewModel.secureStorage.getPasswordProtectionEnabled()
        )
    }
    var showPasswordDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (showPasswordDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                showPasswordDialog = false
                isPasswordProtectionEnabled = false
            },
            content = {
                SetPasswordScreen(
                    onSubmitPassword = { password ->
                        viewModel.secureStorage.savePassword(password)
                        scope.launch {
                            snackbarHostState.showSnackbar("App protection enabled.")
                            MyAppWidget().updateAll(context = context)
                        }
                        showPasswordDialog = false
                    },
                )
            }
        )

    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState)  },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(11.dp)
                        .clickable { showDialog = true }) {
                    Text(
                        text = stringResource(id = R.string.apperance),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(id = viewModel.themeSetting.theme.getStringResId()),
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
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
                            } else {
                                isPasswordProtectionEnabled = false
                                viewModel.secureStorage.clearStoredPassword()
                                scope.launch {
                                    snackbarHostState.showSnackbar("App protection disabled.")
                                    MyAppWidget().updateAll(context = context)
                                }
                            }
                        }
                    )
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
                    Text(
                        text = stringResource(id = R.string.backup),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(start = 11.dp),
                    thickness = 0.8.dp,
                    color = Color.LightGray
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
                    Text(
                        text = stringResource(id = R.string.restore),
                        style = MaterialTheme.typography.bodyLarge
                    )
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
                    Text(
                        text = stringResource(id = R.string.about),
                        style = MaterialTheme.typography.bodyLarge
                    )
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
fun SetPasswordScreen(onSubmitPassword: (String) -> Unit) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Card {
        Column(
            modifier = Modifier
                .clipToBounds()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter Password") },
                modifier = Modifier.padding(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                suffix = if (password.isEmpty()) null else {
                    {
                        IconButton(
                            onClick = {
                                password = ""
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Rounded.Clear,
                                contentDescription = "Clear Password"
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password,
                )
            )
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.padding(16.dp),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                isError = !passwordError.isNullOrEmpty(),
                supportingText = if (passwordError.isNullOrEmpty()) null else {
                    { Text(passwordError!!) }
                },
                suffix = if (confirmPassword.isEmpty()) null else {
                    {
                        IconButton(
                            onClick = {
                                confirmPassword = ""
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Rounded.Clear,
                                contentDescription = "Clear Password"
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = password.isNotEmpty() && confirmPassword.isNotEmpty(),
                onClick = {
                    if (password == confirmPassword && password.isNotBlank()) {
                        passwordError = null
                        onSubmitPassword(password)
                    } else {
                        passwordError = "Password didn't match"
                    }
                }
            ) {
                Text(text = "Set Password", color = Color.White)
            }
        }
    }
}
