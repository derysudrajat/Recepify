package id.derysudrajat.recepify.feature.result

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import id.derysudrajat.recepify.utils.ItemsData
import id.derysudrajat.recepify.utils.RecipeData
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
    onBack: () -> Unit
) {
    val indicationBack = remember { MutableInteractionSource() }
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = AppColor.Main.Light
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
                            .data(R.drawable.ic_launcher_background)
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
                        .padding(16.dp),
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
                state = pagerState
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
                                    recipeData.description
                                )
                            }
                            items(recipeData.ingredients) { ingredient ->
                                Column {
                                    Text(
                                        ingredient.title
                                    )
                                    ingredient.items.forEach { item ->
                                        Column(
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        ) {
                                            Text(item.item)
                                            Text(item.quantity)
                                            Text(item.notes)
                                        }
                                    }
                                }
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
                        )
                    )
                )
            )
        ),
        onBack = {}
    )
}
