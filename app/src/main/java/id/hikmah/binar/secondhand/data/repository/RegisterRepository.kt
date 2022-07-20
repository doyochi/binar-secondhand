package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterRepository(private val api: ApiService) {
    suspend fun registerAccount(fullName: String, email: String, password: String) = api.registerAccount(getRequestBody(fullName, email, password))

    private fun getRequestBody(fullName: String, email: String, password: String) : RequestBody {
        val requestBody = MultipartBody.Builder().apply {
            setType(MultipartBody.FORM)
            addFormDataPart("full_name", fullName)
            addFormDataPart("email", email)
            addFormDataPart("password", password)
        }.build()
        return requestBody
    }
}