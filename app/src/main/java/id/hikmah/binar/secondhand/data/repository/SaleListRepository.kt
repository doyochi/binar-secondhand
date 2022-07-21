package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService

class SaleListRepository(private val api: ApiService) {

    suspend fun getSoldProduct(accessToken: String) =
        api.fetchSoldProduct(getHeaderMap(accessToken))

    suspend fun fetchUsers(accessToken: String) = api.getUser(accessToken)
    suspend fun fetchProductSeller(accessToken: String) =
        api.fetchSellerProduct(getHeaderMap(accessToken))

    suspend fun fetchProductSellerDetails(accessToken: String, id: Int) =
        api.fetchSellerProduct(getHeaderMap(accessToken), id)

    private fun getHeaderMap(accessToken: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Connection"] = "keep-alive"
        headerMap["Accept"] = "*/*"
        headerMap["Accept-Encoding"] = "gzip, deflate, br"
        headerMap["access_token"] = accessToken
        return headerMap
    }
}