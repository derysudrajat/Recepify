package id.derysudrajat.recepify

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import id.derysudrajat.recepify.ui.theme.AppColor

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppButtonCookie(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec<Float>()
    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp, minWidth = 48.dp)
            .sizeIn(
                minHeight = 48.dp,
                maxHeight = ButtonDefaults.ExtraLargeContainerHeight,
                minWidth = 48.dp,
                maxWidth = ButtonDefaults.ExtraLargeContainerHeight,
            )
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .expIndication(interactionSource)
            .clip(MaterialShapes.Cookie9Sided.toShape())
            .background(
                AppColor.Main.Alternative,
                MaterialShapes.Cookie9Sided.toShape(),
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = Color.White),
                onClick = onClick,
                role = Role.Button,
                enabled = true,
                onClickLabel = "",
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}