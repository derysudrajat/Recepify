package id.derysudrajat.recepify.feature.result

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import id.derysudrajat.recepify.AppMainToggle
import id.derysudrajat.recepify.R
import id.derysudrajat.recepify.expClickable
import id.derysudrajat.recepify.expIndication
import id.derysudrajat.recepify.ui.theme.AppColor
import id.derysudrajat.recepify.ui.theme.Typography
import id.derysudrajat.recepify.utils.IngredientsData
import id.derysudrajat.recepify.utils.InstructionsData
import id.derysudrajat.recepify.utils.ItemsData
import id.derysudrajat.recepify.utils.RecipeData
import id.derysudrajat.recepify.utils.ServingSuggestionData
import kotlinx.coroutines.launch

private object ResultId {
    data object Header
    data object Pager
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ResultContent(
    image: String,
    recipeData: RecipeData,
    onBack: () -> Unit,
) {
    val indicationBack = remember { MutableInteractionSource() }
    val pagerState = rememberPagerState { 2 }
    val paddingStatusBar = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val paddingNavigation = WindowInsets.navigationBars.asPaddingValues().calculateTopPadding()
    val scope = rememberCoroutineScope()
    ConstraintLayout(
        modifier = Modifier
            .background(AppColor.Main.Light)
            .fillMaxSize(),
        constraintSet = ConstraintSet {
            val header = createRefFor(ResultId.Header)
            val pager = createRefFor(ResultId.Pager)
            constrain(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(pager) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId(ResultId.Header)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp, max = 250.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.ifBlank { R.drawable.ic_launcher_background })
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                AppMainToggle(
                    options = listOf("Bahan-Bahan", "Cara Pembuatan"),
                    onSelectedChange = {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = paddingStatusBar),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .expIndication(indicationBack)
                        .background(AppColor.Main.Light, CircleShape)
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
                        tint = AppColor.Main.Alternative
                    )
                }

                Box(
                    modifier = Modifier
                        .background(AppColor.Main.Alternative, CircleShape)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = recipeData.recipeName,
                        color = AppColor.Main.Light,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Typography.titleSmallEmphasized,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        HorizontalPager(
            modifier = Modifier.layoutId(ResultId.Pager),
            state = pagerState,
            userScrollEnabled = false
        ) {
            when (it) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Text(
                                text = recipeData.description,
                                style = Typography.bodyMediumEmphasized
                            )
                        }
                        items(recipeData.ingredients) { ingredient ->
                            Column {
                                Text(
                                    text = ingredient.title,
                                    fontWeight = FontWeight.SemiBold,
                                    style = Typography.bodyLargeEmphasized
                                )
                                ingredient.items.forEach { item ->
                                    Column(
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    ) {
                                        listOf(item.item, item.quantity, item.notes).forEach { d ->
                                            if (d.isNotBlank()) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(10.dp)
                                                            .background(
                                                                color = AppColor.Main.Primary,
                                                                shape = MaterialShapes.Cookie6Sided.toShape()
                                                            )
                                                    )
                                                    Text(
                                                        text = d,
                                                        style = Typography.bodyMediumEmphasized
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars))
                        }
                    }
                }

                1 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(recipeData.instructions) { instruction ->
                            Column {
                                Text(
                                    text = instruction.title,
                                    fontWeight = FontWeight.SemiBold,
                                    style = Typography.bodyLargeEmphasized
                                )
                                instruction.steps.forEach { d ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 5.dp)
                                                .size(10.dp)
                                                .background(
                                                    color = AppColor.Main.Primary,
                                                    shape = MaterialShapes.Cookie6Sided.toShape()
                                                )
                                        )
                                        Text(
                                            text = d,
                                            style = Typography.bodyMediumEmphasized
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Column {
                                Text(
                                    text = recipeData.servingSuggestion.title,
                                    fontWeight = FontWeight.SemiBold,
                                    style = Typography.titleMediumEmphasized
                                )
                                recipeData.servingSuggestion.items.forEach { d ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 5.dp)
                                                .size(10.dp)
                                                .background(
                                                    color = AppColor.Main.Primary,
                                                    shape = MaterialShapes.Cookie6Sided.toShape()
                                                )
                                        )
                                        Text(
                                            text = d,
                                            style = Typography.bodyMediumEmphasized
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(Modifier.windowInsetsPadding(WindowInsets.navigationBars))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewResultContent() {
    ResultContent(
        image = "",
        recipeData = RecipeData.Empty.copy(
            recipeName = "Ayam Geprek Sambal Bawang",
            description = "Resep dinamis untuk membuat ayam goreng tepung krispi yang digeprek dengan sambal bawang pedas, menghasilkan hidangan yang renyah, gurih, dan nikmat.",
            ingredients = listOf(
                IngredientsData(
                    title = "Bahan Marinasi Ayam",
                    items = listOf(
                        ItemsData(
                            "Ayam",
                            "1/2 ekor (sekitar 500 gr)\"",
                            "Potong menjadi 4-5 bagian"
                        ),
                        ItemsData(
                            "Ayam",
                            "1/2 ekor (sekitar 500 gr)\"",
                            "Potong menjadi 4-5 bagian"
                        )
                    )
                )
            ),
            instructions = listOf(
                InstructionsData(
                    title = "Langkah 1",
                    steps = listOf(
                        "buat makanan saidsakjd"
                    )
                )
            ),
            servingSuggestion = ServingSuggestionData(
                title = "",
                items = listOf()
            )
        ),
        onBack = {}
    )
}
