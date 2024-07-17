package rocks.poopjournal.fucksgivenwatch.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Alert
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.composables.DatePicker
import rocks.poopjournal.fucksgivenwatch.R
import rocks.poopjournal.fucksgivenwatch.data.FuckData
import rocks.poopjournal.fucksgivenwatch.presentation.theme.ListColor
import rocks.poopjournal.fucksgivenwatch.presentation.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun WearApp() {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val list = uiState.fuckList
    val groupedFucks = list.groupBy { getFormattedDateLocal(it.date) }
    var addTaskDialogOpen by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Button(
                onClick = {addTaskDialogOpen = true},
                modifier = Modifier.size(34.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "add",
                    tint = Color.White
                )
            }
            if (list.isNotEmpty()) {
                ScalingLazyColumn {
                    groupedFucks.forEach { (date, fucks) ->
                        // Header for each date
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                                    .background(MaterialTheme.colorScheme.background),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                val headerText = if (isToday(date)) {
                                    "Today"
                                } else {
                                    date
                                }
                                Text(
                                    text = headerText,
                                    modifier = Modifier.padding(start = 8.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                        // Items under each date
                        items(fucks) { fuck ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = ListColor,
                                            shape = RoundedCornerShape(26.dp)
                                        ),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = fuck.description,
                                            modifier = Modifier.padding(start = 8.dp),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No Fucks Given",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        if (addTaskDialogOpen) {
            AddDialog(onDismiss = { addTaskDialogOpen = false }) { data ->
                viewModel.addFuck(data, context)
            }
        }

    }
}

fun getFormattedDate(timestamp: Long): String {
    val date = Date(timestamp)
    val sdf = SimpleDateFormat("MMMM dd", Locale.getDefault())
    return sdf.format(date)
}
fun getFormattedDateLocal(epochDay: Long): String {
    val date = LocalDate.ofEpochDay(epochDay)
    return date.format(DateTimeFormatter.ofPattern("MMMM dd"))
}

fun isToday(dateString: String): Boolean {
    val todayString = getFormattedDate(System.currentTimeMillis())
    return dateString == todayString
}

@Composable
fun AddDialog(
    onDismiss: () -> Unit,
    onAdd: (FuckData) -> Unit,
) {
    var description by remember { mutableStateOf("") }
    var dateDialogOpen by remember {
        mutableStateOf(false)
    }
    var date by remember {
        mutableLongStateOf(0)
    }

    Alert(
        title = { Text(text = "Add") },
        negativeButton = {
            Button(
                onClick = onDismiss, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = Color.White
                )
            ) {
                Text(text = "Cancel")
            }
        },
        positiveButton = {
            androidx.compose.material3.Button(
                onClick = {
                    onAdd(
                        FuckData(description = description, date = date)
                    )
                    onDismiss()
                }, colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(text = "Add")
            }
        }, content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = description, onValueChange = {
                        description = it
                    }, colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    placeholder = {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            shape = RoundedCornerShape(5.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        .padding(5.dp)
                        .clickable { dateDialogOpen = true }
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                        text = if (date == 0L) "Select Date" else getFormattedDateLocal(date),
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                   Icon(
                        imageVector = Icons.Filled.DateRange, contentDescription = "select date"
                    )

                }
            }

        }
    )
    if(dateDialogOpen) {
        Column(modifier = Modifier.fillMaxSize()) {
            DatePicker(
                onDateConfirm = { selectedDate ->
                    date = selectedDate.toEpochDay()
                    dateDialogOpen = false
                },
                date = LocalDate.now()
            )
        }
    }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DatePickerPreview() {
    DatePicker(
        onDateConfirm = {},
        date = LocalDate.of(2022, 4, 25),
    )
}

@Composable
@Preview(
    device = WearDevices.SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true,
    group = "Fonts - Largest",
    fontScale = 1.24f,
)
fun DatePickerPreviewSmallDeviceWithLargeFontBold() {
        DatePicker(
            onDateConfirm = {},
            date = LocalDate.of(2022, 1, 25),
        )
    }
