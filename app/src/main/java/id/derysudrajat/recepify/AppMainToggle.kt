package id.derysudrajat.recepify

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.recepify.ui.theme.AppColor
import id.derysudrajat.recepify.ui.theme.RecepifyTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppMainToggle(
    options: List<String>,
    modifier: Modifier = Modifier,
    onSelectedChange: (index: Int) -> Unit,
) {

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options.first()) }
    HorizontalFloatingToolbar(
        modifier = modifier,
        colors = FloatingToolbarColors(
            toolbarContainerColor = AppColor.Main.Secondary,
            toolbarContentColor = AppColor.Main.Light,
            fabContainerColor = AppColor.Main.Light,
            fabContentColor = AppColor.Main.Light,
        ),
        expanded = true,
    ) {
        options.forEachIndexed { index, label ->
            ToggleButton(
                modifier = Modifier,
                checked = selectedOption == label,
                onCheckedChange = {
                    onOptionSelected(label)
                    onSelectedChange(index)
                },
                shapes = ToggleButtonDefaults.shapes(checkedShape = MaterialTheme.shapes.large),
                colors = ToggleButtonDefaults.toggleButtonColors(
                    checkedContainerColor = AppColor.Main.Alternative,
                    containerColor = AppColor.Main.Secondary,
                ),
            ) {
                Text(
                    label,
                    maxLines = 1,
                    color = if (selectedOption == label) AppColor.Main.Light else AppColor.Main.Alternative
                )
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
        AppMainToggle(
            options = listOf("Bahan-Bahan", "Cara Pembuatan"),
            onSelectedChange = {}
        )
    }
}