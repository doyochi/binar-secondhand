package id.hikmah.binar.secondhand.data.remote.model.notification

import com.google.gson.annotations.SerializedName

data class ProductSold(
    @SerializedName("price")
    val price: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int
)
