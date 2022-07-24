package id.hikmah.binar.secondhand.data.repository

import android.media.Image
import id.hikmah.binar.secondhand.data.remote.model.dto.User
import id.hikmah.binar.secondhand.data.remote.model.notification.UserError
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Multipart

class UserRepo (private val apiService: ApiService) {

    suspend fun getUser(accessToken: String) = apiService.getUser(getHeaderMap(accessToken))

    suspend fun changePasswordUser(accessToken: String,
                                   oldPass: RequestBody,
                                   newPass: RequestBody,
                                   newPassConfirm: RequestBody
    ) : UserError {
        return apiService.changePassword(getHeaderMap(accessToken), oldPass, newPass, newPassConfirm)
    }


    suspend fun editUser(accessToken: String,
                         fullName: RequestBody?,
                         city: RequestBody?,
                         address: RequestBody?,
                         phoneNumber: RequestBody?,
                         image: MultipartBody.Part?
    ) = apiService.editUser(getHeaderMap(accessToken),fullName, city, address, phoneNumber,image)

    private fun getHeaderMap(accessToken: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Connection"] = "keep-alive"
        headerMap["Accept"] = "*/*"
        headerMap["Accept-Encoding"] = "gzip, deflate, br"
        headerMap["access_token"] = accessToken
        return headerMap
    }
}