package rocks.poopjournal.fucksgiven.presentation.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.presentation.component.AppBar
import rocks.poopjournal.fucksgiven.presentation.component.BottomBar
import rocks.poopjournal.fucksgiven.presentation.component.BottomNavBar
import rocks.poopjournal.fucksgiven.presentation.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var addTaskDialogOpen by remember {
        mutableStateOf(false)
    }

    val storagePermission = rememberPermissionState(
        permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted
        } else {
            // Handle permission denial
        }
    }

    LaunchedEffect(key1 = storagePermission) {
        if (!storagePermission.status.isGranted && storagePermission.status.shouldShowRationale) {
            // Show rationale if needed
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }


    Scaffold(topBar = { AppBar(title = "Fucks Given", navigate = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addTaskDialogOpen = true },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        bottomBar = {
            BottomNavBar(navHostController = navController, items = BottomBar.getMenuBottomItems())
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
        ) {
            if (addTaskDialogOpen) {
                AddDialog(onDismiss = { addTaskDialogOpen = false }) { data ->
                    viewModel.addFuck(data,context)
                }
            }
            if (uiState.fuckList.isNotEmpty()) {
                FucksList(fuckList = uiState.fuckList)
            } else {
                Text(text = "No Fucks Given", modifier = Modifier.padding(12.dp))
            }
        }
    }
}

@Composable
fun FucksList(
    fuckList: List<FuckData>
) {
    // Group the fuckList by date
    val groupedFucks = fuckList.groupBy { getFormattedDate(it.date) }

    LazyColumn {
        groupedFucks.forEach { (date, fucks) ->
            // Header for each date
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val headerText = if (isToday(date)) {
                        "Today"
                    } else {
                        date
                    }
                    Text(
                        text = headerText,
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.primary,
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = fuck.description,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
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

    val datePickerState = rememberDatePickerState()

    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(
            onClick = {
                onAdd(
                    FuckData(description = description, date = date)
                )
                onDismiss()
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(text = "Add")
        }
    }, dismissButton = {
        TextButton(
            onClick = onDismiss, colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Cancel")
        }
    }, containerColor = MaterialTheme.colorScheme.background, text = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            OutlinedTextField(
                value = description, onValueChange = {
                    description = it
                }, colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                placeholder = {
                    Text(text = "Description", style = MaterialTheme.typography.bodyLarge)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

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
                        text = if (date == 0L) "Select Date" else getFormattedDate(date),
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date")
                }


            if (dateDialogOpen) {
                DatePickerDialog(onDismissRequest = { dateDialogOpen = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                date = datePickerState.selectedDateMillis ?: 0L
                                dateDialogOpen = false
                            }) {
                            Text(text = "Ok")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { dateDialogOpen = false }) {
                            Text(text = "Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState, colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.background,
                            headlineContentColor = MaterialTheme.colorScheme.primary,
                            dayContentColor = MaterialTheme.colorScheme.primary,
                            yearContentColor = MaterialTheme.colorScheme.primary,
                            todayContentColor = MaterialTheme.colorScheme.primary,
                            selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            }
        }
    })
}

fun getFormattedDate(timestamp: Long): String {
    val date = Date(timestamp)
    val sdf = SimpleDateFormat("MMMM dd", Locale.getDefault())
    return sdf.format(date)
}

fun isToday(dateString: String): Boolean {
    val todayString = getFormattedDate(System.currentTimeMillis())
    return dateString == todayString
}


