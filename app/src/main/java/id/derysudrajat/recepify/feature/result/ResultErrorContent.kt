package id.derysudrajat.recepify.feature.result

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import id.derysudrajat.recepify.R
import id.derysudrajat.recepify.expClickable
import id.derysudrajat.recepify.expIndication
import id.derysudrajat.recepify.ui.theme.AppColor
import id.derysudrajat.recepify.ui.theme.Typography


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ResultErrorContent(
    errorMessage: String,
    onBack: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_failed_food))
    val indication = remember { MutableInteractionSource() }
    val indicationBack = remember { MutableInteractionSource() }
    Scaffold(
        containerColor = AppColor.Main.Light,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .expIndication(indicationBack)
                    .background(AppColor.Main.Alternative, CircleShape)
                    .expClickable(
                        indicationBack,
                        onClick = onBack
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = AppColor.Main.Light
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    modifier = Modifier.size(200.dp),
                    iterations = LottieConstants.IterateForever,
                    composition = composition
                )
                Spacer(Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = errorMessage,
                    textAlign = TextAlign.Center,
                    style = Typography.titleLargeEmphasized,
                    color = AppColor.Main.Alternative
                )
                Spacer(Modifier.height(48.dp))
                Box(
                    modifier = Modifier
                        .expIndication(indication)
                        .background(AppColor.Main.Alternative, RoundedCornerShape(16.dp))
                        .expClickable(indication, onClick = onBack)
                        .padding(horizontal = 48.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Re-Upload !",
                        style = Typography.titleLargeEmphasized,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColor.Main.Light
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewResultErrorContent() {
    ResultErrorContent(
        errorMessage = "Gambar bukan gambar sembarang gambar",
        onBack = {}
    )
}