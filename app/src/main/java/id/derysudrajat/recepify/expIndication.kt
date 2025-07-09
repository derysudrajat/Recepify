package id.derysudrajat.recepify

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }


@Composable
fun Modifier.dashedRoundedRectBorder(
    width: Dp,
    color: Color,
    cornerRadius: Dp,
    intervals: FloatArray = floatArrayOf(10f.dp.dpToPx(), 10f.dp.dpToPx()),
    phase: Float = 0f,
): Modifier =
    this.then(
        Modifier.drawBehind {
            val stroke = Stroke(
                width = width.toPx(),
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(intervals, phase),
            )
            drawRoundRect(
                color,
                style = stroke,
                cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
            )
        },
    )