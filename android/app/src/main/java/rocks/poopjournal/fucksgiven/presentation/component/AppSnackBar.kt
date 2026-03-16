package rocks.poopjournal.fucksgiven.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun AppSnackbar(snackbarData: SnackbarData) {
    Snackbar(
        snackbarData = snackbarData,
        shape = RoundedCornerShape(8.dp),
        containerColor = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.background,
        actionColor = MaterialTheme.colorScheme.primary
    )
}