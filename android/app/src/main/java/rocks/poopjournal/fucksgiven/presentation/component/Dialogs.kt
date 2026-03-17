package rocks.poopjournal.fucksgiven.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.presentation.ui.utils.formatDate
import rocks.poopjournal.fucksgiven.presentation.ui.utils.isToday
import rocks.poopjournal.fucksgiven.presentation.ui.utils.toUtcEpochMillis
import rocks.poopjournal.fucksgiven.presentation.ui.utils.utcMillisToLocalDate
import java.time.LocalDate


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
    val today = remember { LocalDate.now() }

    var selectedDate by remember { mutableStateOf(today) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today.toUtcEpochMillis()
    )

    val dateText = if (isToday(selectedDate)) {
        stringResource(R.string.today)
    } else {
        formatDate(selectedDate)
    }

    val isValid = description.isNotBlank()

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            OutlinedTextField(
                value = description, onValueChange = {
                    description = it
                }, colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.background,
                    unfocusedBorderColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedPlaceholderColor = Color.LightGray,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.description),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            dateDialogOpen = true
                        }
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = stringResource(
                                id = R.string.select_date
                            ),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(
                            dateText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (isValid) MaterialTheme.colorScheme.primary else Color.LightGray)
                        .size(40.dp)
                        .clickable(enabled = isValid) {
                            val date = selectedDate
                            onAdd(
                                FuckData(
                                    description = description,
                                    date = date
                                )
                            )
                            onDismiss()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = stringResource(R.string.add),
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
            if (dateDialogOpen) {
                DatePickerDialog(
                    onDismissRequest = { dateDialogOpen = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val millis =
                                    datePickerState.selectedDateMillis ?: return@TextButton
                                selectedDate = utcMillisToLocalDate(millis)
                                dateDialogOpen = false

                            }) {
                            Text(text = stringResource(id = R.string.ok))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { dateDialogOpen = false }) {
                            Text(text = stringResource(id = R.string.cancel))
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
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDialog(
    fuckData: FuckData,
    onDismiss: () -> Unit,
    onUpdate: (FuckData) -> Unit,
) {
    var description by remember { mutableStateOf(fuckData.description) }
    var dateDialogOpen by remember { mutableStateOf(false) }
    var selectedDate by remember {
        mutableStateOf(fuckData.date)
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.toUtcEpochMillis()
    )
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    value = description, onValueChange = {
                        description = it
                    }, colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedPlaceholderColor = Color.LightGray,
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.description),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                dateDialogOpen = true
                            }
                            .border(
                                1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = stringResource(
                                    id = R.string.select_date
                                ),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = formatDate(selectedDate),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .size(40.dp)
                            .clickable {
                                onUpdate(
                                    FuckData(
                                        description = description,
                                        date = selectedDate,
                                        id = fuckData.id
                                    )
                                )
                                onDismiss()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = stringResource(R.string.add),
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }

                if (dateDialogOpen) {
                    DatePickerDialog(
                        onDismissRequest = { dateDialogOpen = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val millis =
                                        datePickerState.selectedDateMillis ?: return@TextButton
                                    selectedDate = utcMillisToLocalDate(millis)
                                    dateDialogOpen = false
                                }) {
                                Text(text = stringResource(id = R.string.ok))
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { dateDialogOpen = false }) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState,
                            colors = DatePickerDefaults.colors(
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
        }
    }
}


