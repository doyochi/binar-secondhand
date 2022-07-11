package id.hikmah.binar.secondhand.daftarjual.data.remote.dto.notification


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int
)