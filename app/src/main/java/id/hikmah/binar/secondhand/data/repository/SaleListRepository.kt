package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService

class SaleListRepository(private val api: ApiService) {
    suspend fun getFavoriteProduct(accessToken: String) = api.fetchFavoriteProduct(getHeaderMap(accessToken))
    suspend fun fetchUsers(accessToken: String) = api.getUser(accessToken)
    suspend fun fetchProductSeller(accessToken: String) = api.fetchSellerProduct(getHeaderMap(accessToken))

    private fun getHeaderMap(accessToken: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Connection"] = "keep-alive"
        headerMap["Accept"] = "*/*"
        headerMap["Accept-Encoding"] = "gzip, deflate, br"
        headerMap["access_token"] = accessToken
        return headerMap
    }
}