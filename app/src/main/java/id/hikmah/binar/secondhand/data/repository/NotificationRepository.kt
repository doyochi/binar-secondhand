package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService

class NotificationRepository(private val api: ApiService) {

    suspend fun fetchNotification(accessToken: String) = api.fetchNotification(accessToken)

    suspend fun fetchNotificationById(accessToken: String, id: Int) =
        api.fetchNotification(accessToken, id)

    suspend fun patchNotification(accessToken: String, notificationId: Int) =
        api.patchNotification(accessToken, notificationId)
}