package id.hikmah.binar.secondhand.data.remote.service

import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.*
import retrofit2.http.*

import retrofit2.Response


interface BuyerService {
    @POST("buyer/order")
    fun setBuyerOrder(
        @Header("access_token") token : String,
        @Path("product_id") productId : Int,
        @Path("bid_price") bidPrice : Int
    ) : BuyerOrderDto

    @GET("buyer/order")
    fun getBuyerOrder(
        @Header("access_token") token : String,
    ) : BuyerOrderDto

    @GET("buyer/order/{id}")
    suspend fun getBuyerOrderById(
        @Header("access_token") token : String,
        @Path("id") id : Int
    ) : BuyerOrderDtoItem

    @PUT("buyer/order/{id}")
    fun updateBuyerOrder(
        @Header("access_token") token : String,
        @Path("id") id : Int,
        @Path("bid_price") bidPrice : Int
    ) : BuyerOrderDtoPutPost

    @DELETE("buyer/order/{id}")
    fun deleteBuyerOrder(
        @Header("access_token") token: String,
        @Path("id") id : Int
    ) : BuyerOrderDtoDelete

    @GET("buyer/product")
    fun getBuyerProduct() : BuyerProductDto

    @POST("buyer/order")
    suspend fun postBuyerOrder(
        @Body postBuyerOrderBody: PostBuyerOrderBody,
        @Header("access_token") token : String) : Response<PostBuyerOrderDto>


    @GET("buyer/product/{id}")
    suspend fun getBuyerProductById(
        @Path("id") id : Int
    ) : BuyerProductDtoItem

    @GET("buyer/product")
    suspend fun getBuyerProduct(
        @Query("status") status : String?,
        @Query("category_id") categoryId : Int?,
        @Query("search") search : String?
    ) : BuyerProductDto


}