package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.model.User
import id.hikmah.binar.secondhand.data.remote.service.ApiService

class UserRepo (private val apiService: ApiService) {

    suspend fun getUser(key: String) = apiService.getUser(key)

    suspend fun putUser(user: User, key: String) = apiService.putUser(user,key)
}