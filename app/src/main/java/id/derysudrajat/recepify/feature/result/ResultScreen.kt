package id.derysudrajat.recepify.feature.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import id.derysudrajat.recepify.utils.RecipeData
import id.derysudrajat.recepify.utils.ResultMapper


@Composable
fun ResultScreen(
    image: String,
    result: String,
    onBack: () -> Unit
) {
    val (resultMessage, setResultMessage) = remember { mutableStateOf("") }
    val (recipeData, setRecipeData) = remember { mutableStateOf(RecipeData.Empty) }
    LaunchedEffect(Unit) {
        ResultMapper.validateData(
            result,
            onSuccess = {
                setRecipeData(it)
            },
            onFailed = {
                setResultMessage(it)
            }
        )
    }
    ResultContent(
        image = image,
        recipeData = recipeData,
        onBack = onBack
    )
}