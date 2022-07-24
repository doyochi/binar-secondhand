package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.secondhand.data.repository.NotificationRepository
import id.hikmah.binar.secondhand.helper.Resource
import id.hikmah.binar.secondhand.helper.mapper.toNotification
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    fun fetchNotification(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(
                Resource.success(
                    data = repository.fetchNotification(accessToken).map { it.toNotification() })
            )
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        }
    }

    fun patchNotification(accessToken: String, notificationId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = repository.patchNotification(accessToken, notificationId)))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        }
    }

    fun fetchNotificationById(accessToken: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = repository.fetchNotificationById(accessToken, id)))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        }
    }
}