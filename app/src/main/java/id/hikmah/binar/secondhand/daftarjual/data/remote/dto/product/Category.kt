package id.hikmah.binar.secondhand.daftarjual.data.remote.dto.product


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)