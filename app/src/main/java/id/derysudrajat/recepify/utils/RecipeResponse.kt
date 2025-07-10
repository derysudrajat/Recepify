package id.derysudrajat.recepify.utils

import com.google.gson.annotations.SerializedName

data class RecipeResponse(

    @field:SerializedName("instructions")
    val instructions: List<InstructionsResponse>? = null,

    @field:SerializedName("serving_suggestion")
    val servingSuggestion: ServingSuggestionResponse? = null,

    @field:SerializedName("recipe_name")
    val recipeName: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("ingredients")
    val ingredients: List<IngredientsResponse>? = null
)

fun RecipeResponse.toData() = RecipeData(
    instructions = instructions?.map { it.toData() }.orEmpty(),
    servingSuggestion = servingSuggestion?.toData() ?: ServingSuggestionData.Empty,
    recipeName = recipeName.orEmpty(),
    description = description.orEmpty(),
    ingredients = ingredients?.map { it.toData() }.orEmpty()
)

data class ServingSuggestionResponse(

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("items")
    val items: List<String>? = null
)

fun ServingSuggestionResponse.toData() = ServingSuggestionData(
    title = title.orEmpty(),
    items = items.orEmpty()
)

data class ItemsResponse(

    @field:SerializedName("item")
    val item: String? = null,

    @field:SerializedName("quantity")
    val quantity: String? = null,

    @field:SerializedName("notes")
    val notes: String? = null
)

fun ItemsResponse.toData() = ItemsData(
    item = item.orEmpty(),
    quantity = quantity.orEmpty(),
    notes = notes.orEmpty()
)

data class InstructionsResponse(

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("steps")
    val steps: List<String>? = null
)

fun InstructionsResponse.toData() = InstructionsData(
    title = title.orEmpty(),
    steps = steps.orEmpty()
)

data class IngredientsResponse(

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("items")
    val items: List<ItemsResponse>? = null
)

fun IngredientsResponse.toData() = IngredientsData(
    title = title.orEmpty(),
    items = items?.map { it.toData() }.orEmpty()
)
