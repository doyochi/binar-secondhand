package id.hikmah.binar.secondhand.data.remote.model.notification


import com.google.gson.annotations.SerializedName

data class UserError(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String

)