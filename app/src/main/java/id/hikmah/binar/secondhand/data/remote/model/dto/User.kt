package id.hikmah.binar.secondhand.data.remote.model.dto

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("image_url")
    val image: String,
    @SerializedName("city")
    val city: String
)

