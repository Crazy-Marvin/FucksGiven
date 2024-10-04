package rocks.poopjournal.fucksgiven.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.data.FuckData
import rocks.poopjournal.fucksgiven.presentation.ui.theme.FuckRed
import rocks.poopjournal.fucksgiven.presentation.ui.utils.getFormattedDate

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

    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    val selectedDate = if (date == 0L) System.currentTimeMillis() else date

    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        Button(
            onClick = {
                onAdd(
                    FuckData(description = description, date = selectedDate)
                )
                onDismiss()
            }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(text = stringResource(id = R.string.add))
        }
    }, dismissButton = {
        TextButton(
            onClick = onDismiss, colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = stringResource(id = R.string.cancel))
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
                    Text(
                        text = stringResource(id = R.string.description),
                        style = MaterialTheme.typography.bodyLarge
                    )
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
                    text = if (date == 0L) getFormattedDate(selectedDate) else getFormattedDate(
                        date
                    ),
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    imageVector = Icons.Filled.DateRange, contentDescription = stringResource(
                        id = R.string.select_date
                    )
                )
            }


            if (dateDialogOpen) {
                DatePickerDialog(onDismissRequest = { dateDialogOpen = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                date = datePickerState.selectedDateMillis ?: 0L
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
    })
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
    var date by remember { mutableLongStateOf(fuckData.date) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = fuckData.date)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onUpdate(FuckData(description = description, date = date, id = fuckData.id))
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(text = stringResource(id = R.string.update))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary
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
                        text = if (date == 0L) stringResource(id = R.string.select_date) else getFormattedDate(
                            date
                        ),
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = stringResource(id = R.string.select_date)
                    )
                }

                if (dateDialogOpen) {
                    DatePickerDialog(onDismissRequest = { dateDialogOpen = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    date = datePickerState.selectedDateMillis ?: 0L
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
    )
}

@Composable
fun DeleteDialog(
    fuckData: FuckData,
    onDismiss: () -> Unit,
    onDelete: (FuckData) -> Unit
) {
    val description by remember { mutableStateOf(fuckData.description) }
    val date by remember { mutableLongStateOf(fuckData.date) }
    AlertDialog(
        onDismissRequest = onDismiss, confirmButton = {
            Button(
                onClick = {
                    onDelete(FuckData(description = description, date = date, id = fuckData.id))
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = FuckRed,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(text = stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.delete_dialog))
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(text = "Title:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = fuckData.description, fontWeight = FontWeight.Bold)
                }
            }
        }
    )
}