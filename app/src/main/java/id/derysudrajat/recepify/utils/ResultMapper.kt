package id.derysudrajat.recepify.utils

import com.google.gson.Gson

object ResultMapper {

    fun validateData(
        response: String,
        onSuccess: (RecipeData) -> Unit,
        onFailed: (message: String) -> Unit
    ) {
        val gson = Gson()
        val jsonResponse = gson.fromJson(response, BaseResponse::class.java)
        if (jsonResponse.code == "200") {
            val result = gson.fromJson(gson.toJson(jsonResponse.data), RecipeResponse::class.java)
            onSuccess(result.toData())
        } else {
            onFailed(jsonResponse?.message.orEmpty())
        }
    }
}

