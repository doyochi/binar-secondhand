package id.hikmah.binar.secondhand.domain

data class FavoriteProduct(
    val productImage: String,
    val productName: String,
    val productPrice: Int,
    val productBidPrice: Int?,
    val productTransactionDate: String?,
    val buyerId: Int,
    val id: Int
)
