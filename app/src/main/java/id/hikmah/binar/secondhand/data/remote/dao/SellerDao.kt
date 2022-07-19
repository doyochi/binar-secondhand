package id.hikmah.binar.secondhand.data.remote.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hikmah.binar.secondhand.data.local.ProductSellerEntity
import id.hikmah.binar.secondhand.data.remote.model.domain.SellerCategory
import id.hikmah.binar.secondhand.data.remote.model.domain.SellerOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface SellerDao {
    /** Seller Product */
    @Query("SELECT * FROM ProductSellerEntity")
    fun getProductSellerDetail(): List<ProductSellerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductSellerDetail(sellerProduct: List<ProductSellerEntity>)

    @Query("DELETE FROM ProductSellerEntity")
    fun deleteProductSellerDetail()


    @Query("SELECT * FROM SellerOrder")
    fun getOrderDetail(): Flow<List<SellerOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderDetail(sellerProduct: List<SellerOrder>)

    @Query("DELETE FROM SellerOrder")
    fun deleteOrderDetail()

    /** Seller Category */
    @Query("SELECT * FROM SellerCategory ORDER by id ASC")
    fun getSellerCategory(): Flow<List<SellerCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSellerCategory(sellerCategory: List<SellerCategory>)

    @Query("DELETE FROM SellerCategory")
    fun deleteSellerCategory()
}