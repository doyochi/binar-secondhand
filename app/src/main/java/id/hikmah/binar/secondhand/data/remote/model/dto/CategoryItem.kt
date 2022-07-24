package id.hikmah.binar.secondhand.data.remote.model.dto

import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)