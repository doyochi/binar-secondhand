package id.hikmah.binar.secondhand.data.remote.model.dto.buyer


import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("message")
    val message: String?,
    @SerializedName("name")
    val name: String?
)