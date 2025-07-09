package id.derysudrajat.recepify

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

class CustomButton
{
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CustomButton(modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()
    Box(
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp, minWidth = 48.dp)
            .sizeIn(
                minHeight = 48.dp,
                maxHeight = ButtonDefaults.ExtraLargeContainerHeight,
                minWidth = 48.dp,
                maxWidth = ButtonDefaults.ExtraLargeContainerHeight,
            )
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .indication(interactionSource, ScaleIndicationNodeFactory(animationSpec))
            .background(
                MaterialTheme.colorScheme.onSurface,
                MaterialShapes.Cookie9Sided.toShape(),
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color.White),
                onClick = {
                },
                role = Role.Button,
                enabled = true,
                onClickLabel = "",
            ),
    ) {

    }
}