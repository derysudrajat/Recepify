package id.derysudrajat.recepify.feature

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.rounded.Undo
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Undo
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import id.derysudrajat.recepify.AppButtonCookie
import id.derysudrajat.recepify.dashedRoundedRectBorder
import id.derysudrajat.recepify.expClickable
import id.derysudrajat.recepify.expIndication
import id.derysudrajat.recepify.ui.theme.AppColor
import id.derysudrajat.recepify.ui.theme.RecepifyTheme
import id.derysudrajat.recepify.ui.theme.Typography

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UploadPhotoContent(modifier: Modifier = Modifier) {
    val (selectedImg, setSelectedImg) = remember { mutableStateOf("".toUri()) }
    val indication = remember { MutableInteractionSource() }
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                setSelectedImg(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.White)
                    .dashedRoundedRectBorder(
                        2.dp,
                        MaterialTheme.colorScheme.outline,
                        cornerRadius = 28.dp,
                    )
                    .padding(horizontal = 32.dp, vertical = 72.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                if (selectedImg != "".toUri()) {
                    Box{
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
                                .heightIn(min = 250.dp, max = 300.dp)
                                .padding(bottom = 20.dp),
                            contentScale = ContentScale.Crop,
                        )
                        Row(
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Box(
                                modifier = Modifier
                                    .expIndication(indication)
                                    .background(AppColor.Main.Alternative, CircleShape)
                                    .expClickable(indication, onClick = {})
                                    .padding(horizontal = 32.dp, vertical = 32.dp),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.Undo,
                                    contentDescription = "",
                                    tint = AppColor.Main.Light
                                )
                            }
                        }
                    }
                } else {
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
            Box(
                modifier = Modifier
                    .expIndication(indication)
                    .background(AppColor.Main.Alternative, RoundedCornerShape(16.dp))
                    .expClickable(indication, onClick = {})
                    .padding(horizontal = 48.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Get Recipe!",
                    style = Typography.titleLargeEmphasized,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColor.Main.Light
                )
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
                UploadPhotoContent(modifier = Modifier.padding(16.dp))
            }
        }
    }
}