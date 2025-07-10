package id.derysudrajat.recepify.feature.generate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenerateLoadingViewModel @Inject constructor() : ViewModel() {
    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash")

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