package id.derysudrajat.recepify.utils

data class RecipeData(
    val instructions: List<InstructionsData>,
    val servingSuggestion: ServingSuggestionData,
    val recipeName: String,
    val description: String,
    val ingredients: List<IngredientsData>
) {
    companion object {
        val Empty = RecipeData(
            instructions = emptyList(),
            servingSuggestion = ServingSuggestionData.Empty,
            recipeName = "",
            description = "",
            ingredients = emptyList()
        )
    }
}

data class ServingSuggestionData(
    val title: String,
    val items: List<String>
) {
    companion object {
        val Empty = ServingSuggestionData(
            title = "",
            items = emptyList()
        )
    }
}

data class ItemsData(
    val item: String,
    val quantity: String,
    val notes: String
)

data class InstructionsData(
    val title: String,
    val steps: List<String>
)

data class IngredientsData(
    val title: String,
    val items: List<ItemsData>
)