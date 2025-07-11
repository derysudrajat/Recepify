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
import id.derysudrajat.recepify.utils.LocalProviderUtils
import kotlinx.coroutines.launch
import java.io.File
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

    private val baseResponseSchema = Schema.obj(
        mapOf(
            // Define the envelope fields
            "status" to Schema.string(),
            "code" to Schema.string(),
            "message" to Schema.string(),

            // The 'data' field's schema is the recipe schema defined above
            "data" to jsonSchema
        ),
        // 'data' and 'message' are optional. 'data' might not exist in an error response,
        // and 'message' might not exist in a success response.
        optionalProperties = listOf("data", "message")
    )

    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel(
            modelName = "gemini-2.5-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = baseResponseSchema
            }
        )

    fun generateRecipe(
        file: File,
        onSuccess: (result: String) -> Unit,
        onFailed: () -> Unit
    ) {
        viewModelScope.launch {
            val promptText = """
                    Tugas Anda adalah bertindak sebagai asisten koki yang cerdas. Ikuti instruksi ini dengan saksama:
                    
                    1.  **Analisis Gambar:** Pertama, periksa gambar yang diberikan untuk menentukan apakah objek utamanya adalah jenis makanan atau hidangan.
                    
                    2.  **Kondisi Sukses (Jika Gambar adalah Makanan):**
                        Jika gambar tersebut adalah makanan, buatkan resep lengkapnya untuk membuat makanan seperti pada gambar dengan list bahan bahan dan juga step-step cara membuatnya dalam bahasa Indonesia
                    
                    3.  **Kondisi Gagal (Jika Gambar BUKAN Makanan):**
                        Jika gambar tersebut jelas-jelas bukan makanan (misalnya: mobil, hewan, bangunan, orang), JANGAN mencoba membuat resep. Sebagai gantinya, berikan respons error dengan pesan yang jelas. Jawab HANYA dengan format berikut:
                        {"status" : "error"
                        "code" : "403"
                        "message" : "Gambar yang diberikan tidak teridentifikasi sebagai makanan."
                        "data" : {} }
            """
            val prompt = content {
                image(LocalProviderUtils.getBitmap(file))
                text(promptText)
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