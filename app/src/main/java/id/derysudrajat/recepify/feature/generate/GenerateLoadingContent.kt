package id.derysudrajat.recepify.feature.generate

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
fun GenerateLoadingContent(
    onBack: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_food_loading))
    val indication = remember { MutableInteractionSource() }
    Scaffold(
        containerColor = AppColor.Main.Light
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier,
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
                    text = "Generating your recipe ...",
                    style = Typography.titleLargeEmphasized,
                    color = AppColor.Main.Alternative
                )
                Spacer(Modifier.height(100.dp))
                Box(
                    modifier = Modifier
                        .expIndication(indication)
                        .background(AppColor.Main.Alternative, CircleShape)
                        .expClickable(indication, onClick = onBack)
                        .padding(horizontal = 48.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Cancel",
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
private fun PreviewGenerateLoadingContent() {
    GenerateLoadingContent(
        onBack = {}
    )
}