package id.hikmah.binar.secondhand.service

import id.hikmah.binar.secondhand.model.Category
import id.hikmah.binar.secondhand.model.Product
import id.hikmah.binar.secondhand.model.User
import retrofit2.http.*

interface ApiService {
    @GET("auth/user")
    suspend fun getUser(@Header("access_token") key: String): User

    @PUT("auth/user")
    suspend fun putUser(@Body request: User, @Header("access_token") key: String):User

    //Product
    @GET("buyer/product")
    suspend fun getProduct(
        @Header("access_token") key: String): Product
}
