package id.derysudrajat.recepify

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarColors
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.recepify.ui.theme.RecepifyTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppMainToggle(modifier: Modifier = Modifier) {
    val options by remember { mutableStateOf(listOf("Photo", "Prompt")) }

    val (selectedOption, onOptionSelected) = remember { mutableStateOf("Photo") }
    HorizontalFloatingToolbar(
        modifier = modifier.border(
            2.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = MaterialTheme.shapes.large,
        ),
        colors = FloatingToolbarColors(
            toolbarContainerColor = MaterialTheme.colorScheme.surface,
            toolbarContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            fabContainerColor = MaterialTheme.colorScheme.tertiary,
            fabContentColor = MaterialTheme.colorScheme.onTertiary,
        ),
        expanded = true,
    ) {
        options.forEachIndexed { index, label ->
            ToggleButton(
                modifier = Modifier,
                checked = selectedOption == label,
                onCheckedChange = { onOptionSelected(label) },
                shapes = ToggleButtonDefaults.shapes(checkedShape = MaterialTheme.shapes.large),
                colors = ToggleButtonDefaults.toggleButtonColors(
                    checkedContainerColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            ) {
                Text(label, maxLines = 1)
            }
            if (index != options.size - 1) {
                Spacer(Modifier.width(8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAppMainToggle() {
    RecepifyTheme {
        AppMainToggle()
    }
}