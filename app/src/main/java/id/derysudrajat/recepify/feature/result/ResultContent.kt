package id.derysudrajat.recepify.feature.result

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.derysudrajat.recepify.ui.theme.AppColor

@Composable
fun ResultContent(
    result: String,
) {
    Scaffold(
        containerColor = AppColor.Main.Light
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Text(
                    text = result
                )
            }
        }
    }
}