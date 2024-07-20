package rocks.poopjournal.fucksgiven.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import rocks.poopjournal.fucksgiven.R
import rocks.poopjournal.fucksgiven.presentation.ui.utils.AppTheme


@Composable
fun ThemeContent(
    selectedTheme: AppTheme,
    onItemSelect: (AppTheme) -> Unit,
    modifier: Modifier = Modifier
) {
    val themeItems = listOf(
        RadioItems(
            id = AppTheme.LIGHT.ordinal,
            name = stringResource(id = R.string.light_theme)
        ),
        RadioItems(
            id = AppTheme.DARK.ordinal,
            name = stringResource(id = R.string.dark_theme)
        ),
        RadioItems(
            id = AppTheme.FOLLOW_SYSTEM.ordinal,
            name = stringResource(id = R.string.follow_system)
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RadioGroup(
            items = themeItems,
            selected = selectedTheme.ordinal,
            onItemSelected = { id -> onItemSelect(AppTheme.fromOrdinal(id)) },
        )
    }
}

data class RadioItems(
    var id: Int,
    var name: String,
)

@Composable
fun RadioGroup(
    items: Iterable<RadioItems>,
    selected: Int,
    onItemSelected: ((Int) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .selectableGroup()
    ) {
        items.forEach { item ->
            RadioGroupItems(
                items = item,
                selected = selected == item.id,
                onClick = { onItemSelected?.invoke(item.id) },
                )
        }
    }
}

@Composable
private fun RadioGroupItems(
    items: RadioItems,
    selected: Boolean,
    onClick: ((Int) -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = { onClick?.invoke(items.id) },
                role = Role.RadioButton
            )
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Spacer(modifier = modifier.width(5.dp))
        Text(
            text = items.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
