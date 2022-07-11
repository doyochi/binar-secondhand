package id.hikmah.binar.secondhand.daftarjual.data.repository

import id.hikmah.binar.secondhand.daftarjual.data.remote.client.SaleListApi

class SaleListRepository(private val api: SaleListApi) {
    suspend fun getFavoriteProduct() = api.getFavoriteProduct(getHeaderMap())
    suspend fun fetchUsers() = api.fetchUserDetails(getHeaderMap())
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