package id.derysudrajat.recepify.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import id.derysudrajat.recepify.R
import id.derysudrajat.recepify.expClickable
import id.derysudrajat.recepify.expIndication
import id.derysudrajat.recepify.ui.theme.AppColor
import id.derysudrajat.recepify.ui.theme.AppStatusBarColor
import id.derysudrajat.recepify.ui.theme.RecepifyTheme
import id.derysudrajat.recepify.ui.theme.Typography

private object HomeScreenId {
    data object Animation
    data object Illustration

    data object Button

    data object Text
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeContent(
    toUpload: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    AppStatusBarColor()
    Scaffold(
        containerColor = AppColor.Main.Light
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            constraintSet = ConstraintSet {
                val animation = createRefFor(HomeScreenId.Animation)
                val illustration = createRefFor(HomeScreenId.Illustration)
                val button = createRefFor(HomeScreenId.Button)
                val text = createRefFor(HomeScreenId.Text)
                constrain(illustration) {
                    bottom.linkTo(parent.bottom, (-250).dp)
                    start.linkTo(parent.start, margin = (-340).dp)
                    end.linkTo(parent.end, margin = (-400).dp)
                    width = Dimension.fillToConstraints
                }
                constrain(animation) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, (-100).dp)
                }
                constrain(button) {
                    bottom.linkTo(parent.bottom, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                constrain(text) {
                    top.linkTo(parent.top, 150.dp)
                    start.linkTo(parent.start, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    width = Dimension.fillToConstraints
                }
            }
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_food))
            LottieAnimation(
                modifier = Modifier.layoutId(HomeScreenId.Animation),
                iterations = LottieConstants.IterateForever,
                composition = composition
            )
            Image(
                modifier = Modifier.layoutId(HomeScreenId.Illustration),
                painter = painterResource(R.drawable.bg_abs_home),
                contentDescription = ""
            )

            Box(
                modifier = Modifier
                    .layoutId(HomeScreenId.Button)
                    .expIndication(interactionSource)
                    .background(AppColor.Main.Light, CircleShape)
                    .expClickable(interactionSource, onClick = toUpload)
                    .padding(horizontal = 48.dp, vertical = 16.dp)
            ) {
                Text(
                    "Let's go",
                    style = Typography.headlineLargeEmphasized,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColor.Main.Primary
                )
            }

            Column(
                modifier = Modifier.layoutId(HomeScreenId.Text),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Find out the recipe your favorite food",
                    style = Typography.headlineLargeEmphasized,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Take a picture your food and We will find the recipe for you!",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewHomeContent() {
    RecepifyTheme {
        HomeContent(
            toUpload = {}
        )
    }
}