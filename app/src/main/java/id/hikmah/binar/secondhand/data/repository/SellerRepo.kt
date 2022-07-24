package id.hikmah.binar.secondhand.data.repository

import androidx.room.withTransaction
import id.hikmah.binar.secondhand.data.common.networkBoundResource
import id.hikmah.binar.secondhand.data.local.DatabaseSecondHand
import id.hikmah.binar.secondhand.data.remote.model.dto.Category
import id.hikmah.binar.secondhand.data.remote.model.dto.CategoryItem
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.helper.mapper.SellerCategoryMapper
import id.hikmah.binar.secondhand.helper.mapper.SellerProductMapper

class SellerRepo(
    private val service: ApiService,
    private val mapper: SellerProductMapper,
    private val categoryMapper: SellerCategoryMapper,
    private val mDb : DatabaseSecondHand

){
    private val sellerDao = mDb.sellerDao()

    suspend fun getCategory(): List<Category> {
        val result = service.getSellerCategory()
        return categoryMapper.toDomainList(result)
    }

//    fun getAllCategory() = networkBoundResource(
//        query = { sellerDao.getSellerCategory() },
//        fetch = { getCategory() },
//        saveFetchResult = { category ->
//            mDb.withTransaction {
//                sellerDao.deleteSellerCategory()
//                sellerDao.insertSellerCategory(category)
//            }
//        }
//    )
}