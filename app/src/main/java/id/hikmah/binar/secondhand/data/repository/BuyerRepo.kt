package id.hikmah.binar.secondhand.data.repository

import androidx.room.withTransaction
import id.hikmah.binar.secondhand.data.common.networkBoundResource
import id.hikmah.binar.secondhand.data.remote.model.domain.BuyerProductDetail
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.PostBuyerOrderBody
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.PostBuyerOrderDto
import id.hikmah.binar.secondhand.data.remote.model.domain.BuyerProductDetailMapper
import id.hikmah.binar.secondhand.data.remote.service.BuyerService
import id.hikmah.binar.secondhand.data.remote.service.DatabaseSecondHand
import retrofit2.Response

class BuyerRepo(
    private val apiService: BuyerService,
    private val detailMapper: BuyerProductDetailMapper,
    private val mDb : DatabaseSecondHand
) {

    suspend fun getBuyerProductById(id : Int) : BuyerProductDetail {
        val result = apiService.getBuyerProductById(id)
        return detailMapper.mapToDomainModel(result)
    }


    fun getProductDetail(id: Int) = networkBoundResource(
        query = {
            mDb.buyerDao().getProductDetail()
        },
        fetch = {
            getBuyerProductById(id)
        },
        saveFetchResult = { product ->
            mDb.withTransaction {
                mDb.buyerDao().deleteProductDetail()
                mDb.buyerDao().insertProductDetail(product)
            }
        }
    )

    suspend fun setBuyerOrder(postBuyerOrderBody: PostBuyerOrderBody, accessToken : String) : Response<PostBuyerOrderDto> {
       return apiService.postBuyerOrder(postBuyerOrderBody,accessToken)
    }




}

