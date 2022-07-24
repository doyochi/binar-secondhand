package id.hikmah.binar.secondhand.data.remote.model.dto

import com.google.gson.annotations.SerializedName

data class ProductPostError(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)