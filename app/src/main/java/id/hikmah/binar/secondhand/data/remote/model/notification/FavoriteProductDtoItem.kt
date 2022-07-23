package id.hikmah.binar.secondhand.data.remote.model.notification


import com.google.gson.annotations.SerializedName

data class FavoriteProductDtoItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("Product")
    val Product: ProductFav,
    @SerializedName("user_id")
    val userId: String,
){
    data class ProductFav(
        @SerializedName("id")
        val id: Int,
        @SerializedName("base_price")
        val basePrice: Int,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("image_name")
        val imageName: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("status")
        val status: String
    )
}