package id.hikmah.binar.secondhand.repo

import id.hikmah.binar.secondhand.model.User
import id.hikmah.binar.secondhand.service.ApiService

class ProductRepo (private val apiService: ApiService) {

    suspend fun getProduct(key: String) = apiService.getProduct(key)

}