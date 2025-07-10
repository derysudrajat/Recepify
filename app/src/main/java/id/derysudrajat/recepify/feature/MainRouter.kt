package id.derysudrajat.recepify.feature

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import id.derysudrajat.recepify.feature.generate.GenerateLoadingScreen
import id.derysudrajat.recepify.feature.home.HomeContent
import id.derysudrajat.recepify.feature.result.ResultContent
import id.derysudrajat.recepify.feature.upload.UploadPhotoContent

private object MainNav {
    data object Home
    data object Upload
    data class Generate(val uriImage: String)
    data class Result(val result: String)
}

@Composable
fun MainRouter(modifier: Modifier = Modifier) {
    val backStack = remember { mutableStateListOf<Any>(MainNav.Home) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        entryProvider = entryProvider {
            entry<MainNav.Home> {
                HomeContent { backStack.add(MainNav.Upload) }
            }
            entry<MainNav.Upload> {
                UploadPhotoContent(
                    onBack = { backStack.removeLastOrNull() },
                    goGenerateRecipe = {
                        backStack.add(MainNav.Generate(it.toString()))
                    }
                )
            }
            entry<MainNav.Generate> {
                GenerateLoadingScreen(
                    uri = it.uriImage,
                    onBack = { backStack.removeLastOrNull() },
                    goResult = { result ->
                        backStack.add(MainNav.Result(result))
                    }
                )
            }
            entry<MainNav.Result> {
                ResultContent(it.result)
            }
        }
    )

}