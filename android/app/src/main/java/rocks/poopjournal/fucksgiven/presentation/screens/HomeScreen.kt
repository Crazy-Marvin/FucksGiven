package rocks.poopjournal.fucksgiven.presentation.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.presentation.component.AddDialog
import rocks.poopjournal.fucksgiven.presentation.component.AppBar
import rocks.poopjournal.fucksgiven.presentation.component.BottomBar
import rocks.poopjournal.fucksgiven.presentation.component.BottomNavBar
import rocks.poopjournal.fucksgiven.presentation.component.DeleteDialog
import rocks.poopjournal.fucksgiven.presentation.component.UpdateDialog
import rocks.poopjournal.fucksgiven.presentation.ui.utils.getFormattedDate
import rocks.poopjournal.fucksgiven.presentation.ui.utils.isToday
import rocks.poopjournal.fucksgiven.presentation.viewmodel.HomeViewModel

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


    Scaffold(topBar = {
        AppBar(
            title = stringResource(id = R.string.app_name),
            navigate = navController
        )
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addTaskDialogOpen = true },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
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
                    viewModel.addFuck(data, context)
                }
            }
            if (uiState.fuckList.isNotEmpty()) {
                FucksList(fuckList = uiState.fuckList, onUpdate = { data ->
                    viewModel.updateFuck(data, context)
                },
                    onDelete = { data ->
                        viewModel.deleteFuck(data)
                    })
            } else {
                Text(
                    text = stringResource(id = R.string.no_fucks),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FucksList(
    fuckList: List<FuckData>,
    onUpdate: (FuckData) -> Unit,
    onDelete: (FuckData) -> Unit
) {
    var updateTaskDialogOpen by remember { mutableStateOf(false) }
    var deleteTaskDialogOpen by remember { mutableStateOf(false) }
    var selectedFuck by remember { mutableStateOf<FuckData?>(null) }
    var longSelectedFuck by remember { mutableStateOf<FuckData?>(null) }
    val sortedFucks =
        fuckList.sortedWith(compareBy({ !isToday(getFormattedDate(it.date)) }, { -it.date }))

    // Group the sorted list by date
    val groupedFucks = sortedFucks.groupBy { getFormattedDate(it.date) }

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
                            .height(50.dp)
                            .combinedClickable(
                                onClick = {
                                    selectedFuck = fuck
                                    updateTaskDialogOpen = true
                                },
                                onLongClick = {
                                    longSelectedFuck = fuck
                                    deleteTaskDialogOpen = true
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = fuck.description,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Divider(
                        color = Color.LightGray,
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
    selectedFuck?.let { fuckData ->
        if (updateTaskDialogOpen) {
            UpdateDialog(
                fuckData = fuckData,
                onDismiss = { updateTaskDialogOpen = false },
                onUpdate = { updatedFuck ->
                    onUpdate(updatedFuck)
                    updateTaskDialogOpen = false
                }
            )
        }
    }

    longSelectedFuck?.let { fuckData ->
        if (deleteTaskDialogOpen) {
            DeleteDialog(
                fuckData = fuckData,
                onDismiss = { deleteTaskDialogOpen = false },
                onDelete = { deleteFuck ->
                    onDelete(deleteFuck)
                    deleteTaskDialogOpen = false
                })
        }
    }
}










