package id.hikmah.binar.secondhand.domain

data class Notification(
    val notifId: Int,
    val productId: Int,
    val productImage: String,
    val productBidPrice: Int?,
    val productName: String,
    val productBasePrice: Int,
    val transactionDate: String?,
    val status: String,
    val orderId: Int,
    val read: Boolean
)