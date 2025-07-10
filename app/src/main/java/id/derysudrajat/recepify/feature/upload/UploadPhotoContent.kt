package id.derysudrajat.recepify.feature.upload

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Undo
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import id.derysudrajat.recepify.AppButtonCookie
import id.derysudrajat.recepify.R
import id.derysudrajat.recepify.dashedRoundedRectBorder
import id.derysudrajat.recepify.expClickable
import id.derysudrajat.recepify.expIndication
import id.derysudrajat.recepify.ui.theme.AppColor
import id.derysudrajat.recepify.ui.theme.RecepifyTheme
import id.derysudrajat.recepify.ui.theme.Typography

private object UploadPhotoId {
    data object Content
    data object ImageTop
    data object ImageBottom

    data object BackButton
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UploadPhotoContent(
    onBack: () -> Unit,
    goGenerateRecipe: (Uri) -> Unit
) {
    val (selectedImg, setSelectedImg) = remember { mutableStateOf("".toUri()) }
    val indication = remember { MutableInteractionSource() }
    val indicationUndo = remember { MutableInteractionSource() }
    val indicationBack = remember { MutableInteractionSource() }
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                setSelectedImg(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    Scaffold(
        containerColor = AppColor.Main.Light
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            constraintSet = ConstraintSet {
                val content = createRefFor(UploadPhotoId.Content)
                val imageTop = createRefFor(UploadPhotoId.ImageTop)
                val imageBottom = createRefFor(UploadPhotoId.ImageBottom)
                val backButton = createRefFor(UploadPhotoId.BackButton)
                constrain(backButton) {
                    top.linkTo(parent.top, 32.dp)
                    start.linkTo(parent.start, 32.dp)
                }
                constrain(content) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    width = Dimension.fillToConstraints

                }
                constrain(imageTop) {
                    top.linkTo(parent.top, (-100).dp)
                    start.linkTo(parent.start, (-100).dp)
                }
                constrain(imageBottom) {
                    bottom.linkTo(parent.bottom, (-100).dp)
                    end.linkTo(parent.end, (-100).dp)
                }
            }
        ) {
            Image(
                modifier = Modifier.layoutId(UploadPhotoId.ImageTop),
                painter = painterResource(R.drawable.bg_abs_main_top),
                contentDescription = ""
            )
            Image(
                modifier = Modifier.layoutId(UploadPhotoId.ImageBottom),
                painter = painterResource(R.drawable.bg_abs_main_bottom),
                contentDescription = ""
            )
            Box(
                modifier = Modifier
                    .expIndication(indicationBack)
                    .layoutId(UploadPhotoId.BackButton)
                    .background(AppColor.Main.Light, CircleShape)
                    .expClickable(
                        indicationBack,
                        onClick = onBack
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = AppColor.Main.Alternative
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId(UploadPhotoId.Content),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (selectedImg != "".toUri()) {
                        Box(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            AsyncImage(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(selectedImg)
                                    .crossfade(false)
                                    .build(),
                                placeholder = null,
                                contentDescription = "",
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.large)
                                    .fillMaxWidth()
                                    .heightIn(min = 300.dp, max = 500.dp),
                                contentScale = ContentScale.Crop,
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .expIndication(indicationUndo)
                                        .background(AppColor.Main.Light, CircleShape)
                                        .expClickable(
                                            indicationUndo,
                                            onClick = { setSelectedImg("".toUri()) })
                                        .padding(horizontal = 24.dp, vertical = 24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.Undo,
                                        contentDescription = "",
                                        tint = AppColor.Main.Alternative
                                    )
                                }
                            }

                        }
                        Box(
                            modifier = Modifier
                                .expIndication(indication)
                                .background(AppColor.Main.Alternative, RoundedCornerShape(16.dp))
                                .expClickable(indication, onClick = {
                                    goGenerateRecipe(selectedImg)
                                })
                                .padding(horizontal = 48.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = "Get Recipe!",
                                style = Typography.titleLargeEmphasized,
                                fontWeight = FontWeight.SemiBold,
                                color = AppColor.Main.Light
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(28.dp))
                                .background(Color.White)
                                .dashedRoundedRectBorder(
                                    2.dp,
                                    MaterialTheme.colorScheme.outline,
                                    cornerRadius = 28.dp,
                                )
                                .padding(horizontal = 32.dp, vertical = 150.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(48.dp)
                        ) {
                            Text(
                                text = "Choose your food photos",
                                style = Typography.titleLargeEmphasized,
                                color = AppColor.Main.Alternative
                            )
                            AppButtonCookie(onClick = {
                                pickMedia.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }) {
                                Icon(
                                    modifier = Modifier.size(48.dp),
                                    imageVector = Icons.Outlined.AddPhotoAlternate,
                                    contentDescription = "",
                                    tint = AppColor.Main.Light
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewUploadPhotoContent() {
    RecepifyTheme {
        Scaffold(
            containerColor = AppColor.Main.Secondary
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                UploadPhotoContent(
                    onBack = {},
                    goGenerateRecipe = {}
                )
            }
        }
    }
}