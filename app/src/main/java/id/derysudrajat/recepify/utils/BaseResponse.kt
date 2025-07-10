package id.derysudrajat.recepify.utils

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: T? = null,
)
