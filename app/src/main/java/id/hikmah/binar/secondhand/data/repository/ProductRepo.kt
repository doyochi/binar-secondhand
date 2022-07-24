package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.model.dto.ProductPostDto
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ProductRepo (private val apiService: ApiService) {

    suspend fun getProduct(key: String) = apiService.getProduct(key)

    suspend fun postProduct(access_token: String,
                            name: RequestBody,
                            description: RequestBody,
                            basePrice: RequestBody,
                            categoryIds: Int,
                            location: RequestBody,
                            image: MultipartBody.Part?
    ) : Response<ProductPostDto> {
        return apiService.terbitkanSellerProduct(access_token, name, description, basePrice, categoryIds, location, image)
    }

}