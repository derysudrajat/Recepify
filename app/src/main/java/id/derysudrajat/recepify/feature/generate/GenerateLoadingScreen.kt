package id.derysudrajat.recepify.feature.generate

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import id.derysudrajat.recepify.utils.LocalProviderUtils

@Composable
fun GenerateLoadingScreen(
    uri: String,
    onBack: () -> Unit,
    goResult: (result: String) -> Unit
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<GenerateLoadingViewModel>()
    LaunchedEffect(uri) {
        if (uri.isNotBlank()) {
            val file = LocalProviderUtils.copyToInternalStorage(
                application = context,
                uri = uri.toUri()
            )
            viewModel.generateRecipe(
                file = file,
                onSuccess = goResult,
                onFailed = {
                    Toast.makeText(context, "Failed generating recipe", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
    GenerateLoadingContent(
        onBack = onBack
    )
}