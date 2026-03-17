package rocks.poopjournal.fucksgiven.presentation.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.presentation.component.AddDialog
import rocks.poopjournal.fucksgiven.presentation.component.AppBar
import rocks.poopjournal.fucksgiven.presentation.component.AppSnackbar
import rocks.poopjournal.fucksgiven.presentation.component.BottomBar
import rocks.poopjournal.fucksgiven.presentation.component.BottomNavBar
import rocks.poopjournal.fucksgiven.presentation.component.UpdateDialog
import rocks.poopjournal.fucksgiven.presentation.ui.utils.formateDateWithYear
import rocks.poopjournal.fucksgiven.presentation.ui.utils.isToday
import rocks.poopjournal.fucksgiven.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    var addTaskDialogOpen by remember {
        mutableStateOf(false)
    }

    val storagePermission = rememberPermissionState(
        permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val itemDuplicateString = stringResource(R.string.fuck_dupplicated)
    val itemRemoveString = stringResource(R.string.fuck_deleted)
    val undoString = stringResource(R.string.undo)

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
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

    }


    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.app_name),
                navigate = navController
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { snackbarData ->
                AppSnackbar(snackbarData)
            }
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
                    viewModel.addFuck(data)
                }
            }
            if (uiState.fuckList.isNotEmpty()) {
                FucksList(
                    fuckList = uiState.fuckList,
                    onUpdate = { data -> viewModel.updateFuck(data) },
                    onDelete = { data ->

                        viewModel.deleteFuck(data)

                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val result = snackbarHostState.showSnackbar(
                                message = itemRemoveString,
                                actionLabel = undoString,
                                duration = SnackbarDuration.Short
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.addFuck(data)
                            }
                        }
                    },
                    onDuplicate = { data ->
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val copied = data.copy(id = 0)
                            val newId = viewModel.addFuck(copied).await()
                            val result = snackbarHostState.showSnackbar(
                                message = itemDuplicateString,
                                actionLabel = undoString,
                                duration = SnackbarDuration.Short
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.deleteFuck(data.copy(id = newId.toInt()))
                            }
                        }
                    }
                )
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
    onDuplicate: (FuckData) -> Unit,
    onDelete: (FuckData) -> Unit
) {
    var selectedFuckId by remember { mutableStateOf<Int?>(null) }

    val (todayFucks, nonToday) = fuckList.partition { isToday(it.date) }
    val groupedFucks = (todayFucks + nonToday.sortedByDescending { it.date }).groupBy { it.date }
    val selectedFuck = selectedFuckId?.let { id -> fuckList.find { it.id == id } }

    LazyColumn {
        groupedFucks.forEach { (date, fucks) ->
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isToday(date)) stringResource(R.string.today) else formateDateWithYear(date),
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            items(items = fucks, key = { it.id }) { fuck ->
                val currentFuck by rememberUpdatedState(fuckList.find { it.id == fuck.id } ?: fuck)
                val onDuplicateCurrent by rememberUpdatedState(onDuplicate)
                val onDeleteCurrent by rememberUpdatedState(onDelete)

                val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        when (value) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                onDuplicateCurrent(currentFuck)
                                false
                            }
                            SwipeToDismissBoxValue.EndToStart -> {
                                onDeleteCurrent(currentFuck)
                                true
                            }
                            else -> false
                        }
                    },
                    positionalThreshold = { totalDistance -> totalDistance * 0.4f }
                )

                SwipeToDismissBox(
                    state = swipeToDismissBoxState,
                    modifier = Modifier.fillMaxSize(),
                    backgroundContent = {
                        when (swipeToDismissBoxState.dismissDirection) {
                            SwipeToDismissBoxValue.StartToEnd -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primary),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_plus_one),
                                        contentDescription = stringResource(R.string.duplicate),
                                        modifier = Modifier.padding(start = 16.dp),
                                        tint = MaterialTheme.colorScheme.background
                                    )
                                }
                            }
                            SwipeToDismissBoxValue.EndToStart -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.errorContainer),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(R.string.remove_item),
                                        modifier = Modifier.padding(end = 16.dp),
                                        tint = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                }
                            }
                            SwipeToDismissBoxValue.Settled -> {}
                        }
                    }
                ) {
                    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clickable {
                                    selectedFuckId = fuck.id
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = currentFuck.description,
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }

    // selectedFuck is always live-derived above — never a stale snapshot
    selectedFuck?.let { fuckData ->
        UpdateDialog(
            fuckData = fuckData,
            onDismiss = { selectedFuckId = null },
            onUpdate = { updatedFuck ->
                onUpdate(updatedFuck)
                selectedFuckId = null
            }
        )
    }
}










