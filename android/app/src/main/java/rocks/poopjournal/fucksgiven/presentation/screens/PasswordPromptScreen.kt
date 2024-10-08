package rocks.poopjournal.fucksgiven.presentation.screens
import rocks.poopjournal.fucksgiven.data.getPassword

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun PasswordPromptScreen(context: Context, onAuthenticated: () -> Unit) {
    var enteredPassword by remember { mutableStateOf("") }
    val storedPassword = getPassword(context)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
    }
}
