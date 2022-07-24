package id.hikmah.binar.secondhand.data.remote.service

import id.hikmah.binar.secondhand.data.remote.model.dto.*
import id.hikmah.binar.secondhand.data.remote.model.notification.FavoriteProductDto
import id.hikmah.binar.secondhand.data.remote.model.notification.NotificationDto
import id.hikmah.binar.secondhand.data.remote.model.notification.SoldProductDto
import id.hikmah.binar.secondhand.data.remote.model.notification.UserError
import id.hikmah.binar.secondhand.data.remote.model.sellerorder.SellerOrderDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("auth/user")
    suspend fun getUser(@HeaderMap header: Map<String, String>): User

    @Multipart
    @PUT("auth/user")
    suspend fun editUser(@HeaderMap header: Map<String, String>,
                        @Part("full_name") fullName: RequestBody?,
                        @Part("city") city: RequestBody?,
                        @Part("address") address: RequestBody?,
                        @Part ("phone_number") phoneNumber: RequestBody?,
                        @Part image: MultipartBody.Part?): User

    @Multipart
    @PUT("auth/change-password")
    suspend fun changePassword(
        @HeaderMap header: Map<String, String>,
        @Part("current_password") currentPassword: RequestBody,
        @Part("new_password") newPassword: RequestBody,
        @Part("confirm_password") confirmPassword: RequestBody,
    ): UserError


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

    //category
    @GET("seller/category")
    suspend fun getSellerCategory() : List<CategoryItem>

    @GET("seller/category/{id}")
    fun getSellerCategoryId(
        @Path("id") id : Int
    ) : CategoryItem

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

    @Multipart
    @PUT("seller/product")
    suspend fun terbitkanSellerProduct(
        @Header("access_token") accessToken: String,
        @Part("name") productName: RequestBody?,
        @Part("description") productDescription: RequestBody,
        @Part("base_price") productBasePrice: RequestBody,
        @Part("category_ids") productCategory: Int,
        @Part("location") productLocation: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<ProductPostDto>

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
