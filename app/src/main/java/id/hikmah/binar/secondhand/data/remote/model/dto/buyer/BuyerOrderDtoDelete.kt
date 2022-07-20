package id.hikmah.binar.secondhand.data.remote.model.dto.buyer

import com.google.gson.annotations.SerializedName

class BuyerOrderDtoDelete(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)

