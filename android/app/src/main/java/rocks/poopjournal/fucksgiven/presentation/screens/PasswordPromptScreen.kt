package rocks.poopjournal.fucksgiven.presentation.screens

import rocks.poopjournal.fucksgiven.data.getPassword
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.component.BiometricPromptManager

@Composable
fun PasswordPromptScreen(context: Context, onAuthenticated: () -> Unit) {
    var enteredPassword by remember { mutableStateOf("") }
    val storedPassword = getPassword(context)

    val promptManager = remember { BiometricPromptManager(context as AppCompatActivity) }

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
                value = enteredPassword,
                onValueChange = { enteredPassword = it },
                label = { Text("Enter Password") },
                modifier = Modifier.padding(vertical = 16.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            OutlinedButton(onClick = {
                if (storedPassword == enteredPassword) {
                    onAuthenticated()
                } else {
                    Toast.makeText(context, "Password is not correct", Toast.LENGTH_SHORT).show()
                }
            },
                modifier = Modifier.fillMaxWidth(0.3f)
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.fillMaxSize(0.4f))
            OutlinedButton(onClick = {
                promptManager.showBiometricPrompt(
                    title = "Biometric Authentication",
                    description = "Authenticate using your Fingerprint"
                )
            }
                ) {
                Text(text = "Authenticate with Fingerprint")
            }

            biometricResult?.let { result ->
                Text(
                    text = when (result) {
                        is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                            result.error
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                            "Authentication failed"
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                            "Authentication not set"
                        }
                        BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                            "Authentication success"
                        }
                        BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                            "Feature unavailable"
                        }
                        BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                            "Hardware unavailable"
                        }
                    },
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
