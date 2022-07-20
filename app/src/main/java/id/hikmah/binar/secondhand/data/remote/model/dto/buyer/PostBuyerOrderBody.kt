package id.hikmah.binar.secondhand.data.remote.model.dto.buyer


import com.google.gson.annotations.SerializedName

data class PostBuyerOrderBody(
    @SerializedName("bid_price")
    val bidPrice: Int,
    @SerializedName("product_id")
    val productId: Int
)