package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.model.dto.User
import id.hikmah.binar.secondhand.data.remote.service.ApiService

class UserRepo (private val apiService: ApiService) {

    suspend fun getUser(key: String) = apiService.getUser(key)

}