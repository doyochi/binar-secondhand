package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.model.LoginInfo
import id.hikmah.binar.secondhand.data.remote.service.ApiService

class LoginRepository(
    private val api: ApiService) {
    suspend fun loginAccount(email: String, password: String) = api.loginAccount(
        LoginInfo(email, password)
    )
}