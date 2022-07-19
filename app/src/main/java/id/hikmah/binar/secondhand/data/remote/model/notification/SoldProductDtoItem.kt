package id.hikmah.binar.secondhand.data.remote.model.notification


import com.google.gson.annotations.SerializedName

data class SoldProductDtoItem(
    @SerializedName("price")
    val price: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("user_id")
    val userId: String
)