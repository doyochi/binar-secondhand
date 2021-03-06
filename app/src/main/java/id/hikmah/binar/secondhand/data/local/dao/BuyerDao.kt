package id.hikmah.binar.secondhand.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hikmah.binar.secondhand.data.local.entity.BuyerProduct
import id.hikmah.binar.secondhand.data.local.entity.BuyerProductDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface BuyerDao {
    @Query("SELECT * FROM BuyerProductDetail")
    fun getProductDetail(): Flow<BuyerProductDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductDetail(buyerProductDetail: BuyerProductDetail)

    @Query("DELETE FROM BuyerProductDetail")
    fun deleteProductDetail()

    @Query("SELECT * FROM BuyerProduct ORDER by id DESC")
    fun getProduct(): Flow<List<BuyerProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(buyerProduct: List<BuyerProduct>)

    @Query("DELETE FROM BuyerProduct")
    fun deleteProduct()
}