package id.hikmah.binar.secondhand.repo

import id.hikmah.binar.secondhand.model.User
import id.hikmah.binar.secondhand.service.ApiService

class UserRepo (private val apiService: ApiService) {

    suspend fun getUser(key: String) = apiService.getUser(key)

    suspend fun putUser(user:User, key: String) = apiService.putUser(user,key)
}