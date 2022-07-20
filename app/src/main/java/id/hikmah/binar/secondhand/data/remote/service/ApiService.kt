package id.hikmah.binar.secondhand.data.remote.service

import id.hikmah.binar.secondhand.data.remote.model.dto.*
import id.hikmah.binar.secondhand.data.remote.model.notification.FavoriteProductDto
import id.hikmah.binar.secondhand.data.remote.model.notification.SoldProductDto
import id.hikmah.binar.secondhand.data.remote.model.sellerorder.SellerOrderDto
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
    suspend fun fetchFavoriteProduct(@HeaderMap header: Map<String, String>): FavoriteProductDto

    //Get Product Seller
    @GET("seller/product")
    suspend fun fetchSellerProduct(@HeaderMap header: Map<String, String>): List<ProductSellerDto>

    //Seller Order Patch by Id
    @PATCH("seller/order/{id}")
    suspend fun editOrderById(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int,
        @Body status: String
    )

    //Seller Order Get by Id
    @GET("seller/order/{id}")
    suspend fun fetchOrderById(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int
    ): List<SellerOrderDto>

    //Register
    @POST("auth/register")
    suspend fun registerAccount(@Body body: RequestBody)

    //Login
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun loginAccount(@Body loginInfo: LoginInfo): LoginResponse


    //Get Sold list product
    @GET("history")
    suspend fun fetchSoldProduct(@HeaderMap header: Map<String, String>): SoldProductDto
}
