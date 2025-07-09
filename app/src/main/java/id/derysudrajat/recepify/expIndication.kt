package id.derysudrajat.recepify

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import id.derysudrajat.recepify.ui.theme.AppColor


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Modifier.expIndication(
    interactionSource: MutableInteractionSource,
): Modifier {
    val animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()
    return this.indication(interactionSource, ScaleIndicationNodeFactory(animationSpec))
}

@Composable
fun Modifier.expClickable(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    color: Color = AppColor.Main.Light,
): Modifier {
    return this.clickable(
        interactionSource = interactionSource,
        indication = ripple(color = color),
        onClick = onClick,
        role = Role.Button,
        enabled = true,
        onClickLabel = "",
    )
}