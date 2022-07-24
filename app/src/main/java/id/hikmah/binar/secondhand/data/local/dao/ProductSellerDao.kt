package id.hikmah.binar.secondhand.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hikmah.binar.secondhand.data.local.entity.ProductSellerEntity

@Dao
interface ProductSellerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSellerToEntity(productSellerEntity: List<ProductSellerEntity>)

    @Query("SELECT * FROM ProductSellerEntity")
    suspend fun getProductSellerEntity(): List<ProductSellerEntity>

    @Query("DELETE from ProductSellerEntity")
    suspend fun clearProductSeller()
}