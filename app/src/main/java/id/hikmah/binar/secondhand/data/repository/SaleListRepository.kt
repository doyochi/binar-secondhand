package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService

class SaleListRepository(private val api: ApiService) {
    suspend fun getFavoriteProduct() = api.fetchFavoriteProduct(getHeaderMap())
    suspend fun getSoldProduct() = api.fetchSoldProduct(getHeaderMap())
    suspend fun fetchUsers() = api.getUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTY1MDUxMzd9.qD5QoTM_tPgpbGoxgCdmLT3zKrFH1eQy5xMcAvQuWpw")
    suspend fun fetchProductSeller() = api.fetchSellerProduct(getHeaderMap())

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Connection"] = "keep-alive"
        headerMap["Accept"] = "*/*"
        headerMap["Accept-Encoding"] = "gzip, deflate, br"
        headerMap["access_token"] = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTY1MDUxMzd9.qD5QoTM_tPgpbGoxgCdmLT3zKrFH1eQy5xMcAvQuWpw"
        return headerMap
    }

}