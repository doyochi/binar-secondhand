package id.hikmah.binar.secondhand.daftarjual.data.remote.client

import id.hikmah.binar.secondhand.daftarjual.data.remote.dto.notification.FavoriteProductDto
import id.hikmah.binar.secondhand.daftarjual.data.remote.dto.product.ProductSellerDto
import id.hikmah.binar.secondhand.daftarjual.data.remote.dto.users.UsersDto
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface SaleListApi {
    @GET("notification")
    suspend fun getFavoriteProduct(@HeaderMap header: Map<String, String>): FavoriteProductDto

    @GET("auth/user")
    suspend fun fetchUserDetails(@HeaderMap header: Map<String, String>): UsersDto

    @GET("seller/product")
    suspend fun fetchSellerProduct(@HeaderMap header: Map<String, String>): ProductSellerDto
}