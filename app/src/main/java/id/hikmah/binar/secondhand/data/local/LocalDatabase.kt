package id.hikmah.binar.secondhand.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductSellerEntity::class, FavoriteProductEntity::class],
    version = 3
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun productSellerDao(): ProductSellerDao
    abstract fun favoriteProductDao(): FavoriteProductDao
}