package id.hikmah.binar.secondhand.data.remote.model.notification


import com.google.gson.annotations.SerializedName

data class FavoriteProductDtoItem(
    @SerializedName("bid_price")
    val bidPrice: Int,
    @SerializedName("buyer_name")
    val buyerName: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("Product")
    val productFav: ProductFav,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("read")
    val read: Boolean,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("seller_name")
    val sellerName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("updated_at")
    val updatedAt: String
)