package id.hikmah.binar.secondhand.helper.mapper

import id.hikmah.binar.secondhand.data.remote.model.notification.NotificationDto
import id.hikmah.binar.secondhand.domain.Notification

fun NotificationDto.toNotification(): Notification {

    return Notification(
        productId = this.productId,
        productName = this.productName,
        productImage = this.imageUrl,
        productBidPrice = this.bidPrice,
        notifId = this.id,
        transactionDate = this.transactionDate,
        status = this.status,
        read = this.read,
        productBasePrice = this.basePrice.toInt(),
        orderId = this.orderId
    )
}