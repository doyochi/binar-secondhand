package id.hikmah.binar.secondhand.data.remote.service

import id.hikmah.binar.secondhand.data.remote.model.Product
import id.hikmah.binar.secondhand.data.remote.model.User
import id.hikmah.binar.secondhand.data.remote.model.notification.FavoriteProductDto
import id.hikmah.binar.secondhand.data.remote.model.product.ProductSellerDto
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("auth/user")
    suspend fun getUser(@Header("access_token") key: String): User

    @PUT("auth/user")
    suspend fun putUser(@Body request: User, @Header("access_token") key: String): User

    //Product
    @GET("buyer/product")
    suspend fun getProduct(
        @Header("access_token") key: String): Product

    //Get Favorite Product
    @GET("notification")
    suspend fun fetchFavoriteProduct(@HeaderMap header: Map<String, String>): List<FavoriteProductDto>

    //Get Product Seller
    @GET("seller/product")
    suspend fun fetchSellerProduct(@HeaderMap header: Map<String, String>): ProductSellerDto

    //Register
    @POST("auth/register")
    suspend fun registerAccount(@Body body: RequestBody)

    //Login
}
