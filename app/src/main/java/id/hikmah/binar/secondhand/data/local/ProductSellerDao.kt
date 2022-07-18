package id.hikmah.binar.secondhand.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductSellerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSellerToEntity(productSellerEntity: List<ProductSellerEntity>)

    @Query("SELECT * FROM ProductSellerEntity")
    suspend fun getProductSellerEntity(): List<ProductSellerEntity>

    @Query("DELETE from ProductSellerEntity")
    suspend fun clearProductSeller()
}