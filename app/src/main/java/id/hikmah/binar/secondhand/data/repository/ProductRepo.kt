package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService

class ProductRepo (private val apiService: ApiService) {

    suspend fun getProduct(key: String) = apiService.getProduct(key)

}