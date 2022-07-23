package id.hikmah.binar.secondhand.data.remote.service

import id.hikmah.binar.secondhand.data.remote.model.dto.*
import id.hikmah.binar.secondhand.data.remote.model.notification.FavoriteProductDto
import id.hikmah.binar.secondhand.data.remote.model.notification.NotificationDto
import id.hikmah.binar.secondhand.data.remote.model.notification.SoldProductDto
import id.hikmah.binar.secondhand.data.remote.model.sellerorder.SellerOrderDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @GET("auth/user")
    suspend fun getUser(@Header("access_token") key: String): User

    @PUT("auth/user")
    suspend fun putUser(@Header("access_token") key: String,
                        @Part("full_name") name: RequestBody?,
                        @Part("city") kota: RequestBody?,
                        @Part("address") alamat: RequestBody?,
                        @Part ("phone_number")noHP: RequestBody?)

    //Product
    @GET("buyer/product")
    suspend fun getProduct(
        @Header("access_token") key: String
    ): Product

    //Get Product Seller
    @GET("seller/product")
    suspend fun fetchSellerProduct(@HeaderMap header: Map<String, String>): List<ProductSellerDto>

    @GET("seller/product/{id}")
    suspend fun fetchSellerProduct(
        @HeaderMap header: Map<String, String>,
        @Path("id") id: Int
    ): ProductSellerDto

    @Multipart
    @PUT("seller/product/{id}")
    suspend fun editSellerProduct(
        @Header("access_token") accessToken: String,
        @Path("id") id: Int,
        @Part("name") productName: RequestBody?,
        @Part("description") productDescription: RequestBody?,
        @Part("base_price") productBasePrice: RequestBody?,
        @Part("category_ids") productCategory: RequestBody?,
        @Part("location") productLocation: RequestBody?,
        @Part image: MultipartBody.Part?
    )

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
    ): SellerOrderDto

    @GET("seller/order/product/{product_id}")
    suspend fun fetchSellerOrderByProductId(
        @Header("access_token") token: String,
        @Path("product_id") productId: Int
    ): SellerOrderDto

    //Register
    @POST("auth/register")
    suspend fun registerAccount(@Body body: RequestBody)

    //Login
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun loginAccount(@Body loginInfo: LoginInfo): LoginResponse

    //notification
    @GET("notification")
    suspend fun fetchNotification(
        @Header("access_token") token: String
    ): List<NotificationDto>

    @GET("notification/{id}")
    suspend fun fetchNotification(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ): NotificationDto

    @PATCH("notification/{id}")
    suspend fun patchNotification(
        @Header("access_token") token: String,
        @Path("id") notificationId: Int
    )

    //Get Sold list product
    @GET("history")
    suspend fun fetchSoldProduct(@HeaderMap header: Map<String, String>): SoldProductDto

    //Get fav list product
    @GET("buyer/wishlist")
    suspend fun fetchFavProduct(@HeaderMap header: Map<String, String>): FavoriteProductDto
}
