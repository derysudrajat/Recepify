package id.derysudrajat.recepify.feature

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import id.derysudrajat.recepify.AppButtonCookie
import id.derysudrajat.recepify.dashedRoundedRectBorder
import id.derysudrajat.recepify.ui.theme.AppColor
import id.derysudrajat.recepify.ui.theme.RecepifyTheme
import id.derysudrajat.recepify.ui.theme.Typography

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun UploadPhotoContent(modifier: Modifier = Modifier) {
    val (selectedImg, setSelectedImg) = remember { mutableStateOf("".toUri()) }
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

    Column(
        modifier
            .fillMaxWidth()
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
            AsyncImage(
                ImageRequest.Builder(LocalContext.current)
                    .data(selectedImg)
                    .crossfade(false)
                    .build(),
                placeholder = null,
                contentDescription = "",
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
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
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewUploadPhotoContent() {
    RecepifyTheme {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                UploadPhotoContent(modifier = Modifier.padding(16.dp))
            }
        }
    }
}