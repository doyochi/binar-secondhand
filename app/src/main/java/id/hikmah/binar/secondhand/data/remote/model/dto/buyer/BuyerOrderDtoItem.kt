package id.hikmah.binar.secondhand.data.remote.model.dto.buyer

import com.google.gson.annotations.SerializedName
import id.hikmah.binar.secondhand.data.remote.model.Product
import id.hikmah.binar.secondhand.data.remote.model.product.User

data class BuyerOrderDtoItem(
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("User")
    val user: User
)
