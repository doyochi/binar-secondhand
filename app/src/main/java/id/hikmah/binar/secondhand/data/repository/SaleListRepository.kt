package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.helper.Authenticator

class SaleListRepository(
    private val api: ApiService,
    private val auth: Authenticator
) {
    suspend fun fetchFavoriteProduct(accessToken: String) = api.fetchSellerOrderFavorite(getHeaderMap(accessToken))
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

    suspend fun getAccessToken() = auth.getAccessToken()
}