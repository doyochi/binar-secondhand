package id.hikmah.binar.secondhand.data.remote.model.notification


import com.google.gson.annotations.SerializedName

data class ProductFav(
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