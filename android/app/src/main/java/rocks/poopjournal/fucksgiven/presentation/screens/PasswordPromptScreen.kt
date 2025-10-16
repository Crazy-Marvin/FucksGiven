package rocks.poopjournal.fucksgiven.presentation.screens

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.component.BiometricPromptManager
import rocks.poopjournal.fucksgiven.presentation.viewmodel.PasswordPromptViewModel

@Composable
fun PasswordPromptScreen(
    viewModel: PasswordPromptViewModel = hiltViewModel(),
    onAuthenticated: () -> Unit
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val promptManager = remember { BiometricPromptManager(context as AppCompatActivity) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.authenticated.collect {
            onAuthenticated()
        }
    }

    val biometricPromptTitle = stringResource(R.string.biometric_authentication)
    val biometricPromptDescription = stringResource(R.string.biometric_auth_description)
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val biometricResult by promptManager.promptResults.collectAsState(initial = null)
        val enrollLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {
                println("Activity result: $it")
            }
        )

        LaunchedEffect(biometricResult) {
            if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
                if (Build.VERSION.SDK_INT >= 30) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                        )
                    }
                    enrollLauncher.launch(enrollIntent)
                }
            } else if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationSuccess) {
                onAuthenticated() // Call onAuthenticated if biometric authentication succeeds
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxSize(0.2f))

            Image(
                painter = painterResource(R.drawable.fucks),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 24.dp)
            )

            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onChangePassword,
                label = { Text(stringResource(R.string.enter_password)) },
                modifier = Modifier.padding(vertical = 16.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                singleLine = true,
                isError = !state.passwordError.isNullOrEmpty(),
                supportingText = if (state.passwordError.isNullOrEmpty()) null else {
                    { Text(state.passwordError!!) }
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        viewModel.submitPassword()
                    }
                )
            )

            OutlinedButton(
                onClick = {
                    keyboardController?.hide()
                    viewModel.submitPassword()
                },
                modifier = Modifier.fillMaxWidth(0.3f)
            ) {
                Text(text = stringResource(R.string.login))
            }
            Spacer(modifier = Modifier.fillMaxSize(0.4f))
            OutlinedButton(
                onClick = {
                    promptManager.showBiometricPrompt(
                        title = biometricPromptTitle,
                        description = biometricPromptDescription
                    )
                }
            ) {
                Text(text = stringResource(R.string.biometric_auth_btn_label))
            }

            biometricResult?.let { result ->
                Text(
                    text = when (result) {
                        is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                            result.error
                        }

                        BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                            stringResource(R.string.authentication_failed)
                        }

                        BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                            stringResource(R.string.authentication_not_set)
                        }

                        BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                            stringResource(R.string.authentication_success)
                        }

                        BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                            stringResource(R.string.feature_unavailable)
                        }

                        BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                            stringResource(R.string.hardware_unavailable)
                        }
                    },
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
