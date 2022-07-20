package id.hikmah.binar.secondhand.data.repository

import id.hikmah.binar.secondhand.data.remote.service.ApiService

class InfoPenawarRepository(private val api: ApiService) {

    suspend fun patchOrderById(accessToken: String, id: Int, status: String) {
        api.editOrderById(getHeaderMap(accessToken), id, status)
    }

    suspend fun fetchProduct(accessToken: String, id: Int) =
        api.fetchOrderById(getHeaderMap(accessToken), id)

    private fun getHeaderMap(accessToken: String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Connection"] = "keep-alive"
        headerMap["Accept"] = "*/*"
        headerMap["Accept-Encoding"] = "gzip, deflate, br"
        headerMap["access_token"] = accessToken
        return headerMap
    }
}