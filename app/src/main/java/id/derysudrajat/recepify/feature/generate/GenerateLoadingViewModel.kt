package id.derysudrajat.recepify.feature.generate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateLoadingViewModel @Inject constructor() : ViewModel() {
    private val jsonSchema = Schema.obj(
        mapOf(
            // Top-level properties: recipe_name and description are simple strings
            "recipe_name" to Schema.string(),
            "description" to Schema.string(),

            // 'ingredients' is an array of objects
            "ingredients" to Schema.array(
                Schema.obj(
                    mapOf(
                        "title" to Schema.string(),
                        // 'items' is a nested array of ingredient objects
                        "items" to Schema.array(
                            Schema.obj(
                                mapOf(
                                    "item" to Schema.string(),
                                    "quantity" to Schema.string(),
                                    "notes" to Schema.string()
                                ),
                                // The 'notes' field can be null, so we make it optional
                                optionalProperties = listOf("notes")
                            )
                        )
                    )
                )
            ),

            // 'instructions' is an array of objects
            "instructions" to Schema.array(
                Schema.obj(
                    mapOf(
                        "title" to Schema.string(),
                        // 'steps' is a simple array of strings
                        "steps" to Schema.array(Schema.string())
                    )
                )
            ),

            // 'serving_suggestion' is a single object
            "serving_suggestion" to Schema.obj(
                mapOf(
                    "title" to Schema.string(),
                    // 'items' here is a simple array of strings
                    "items" to Schema.array(Schema.string())
                )
            )
        )
    )

    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel(
            modelName = "gemini-2.5-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = jsonSchema
            }
        )

    fun generateRecipe(
        onSuccess: (result: String) -> Unit,
        onFailed: () -> Unit
    ) {
        viewModelScope.launch {
            val prompt = content {
                text("Buatkan resep untuk membuat ayam geprek dengan list bahan bahan dan juga step-step cara membuatnya")
            }
            try {
                val response = model.generateContent(prompt)
                Log.d("TAG", "generateRecipe: ${response.text}")
                onSuccess(response.text.orEmpty())
            } catch (e: Exception) {
                Log.d("TAG", "generateRecipe: failed = ${e.localizedMessage}")
                onFailed()
            }
        }
    }
}