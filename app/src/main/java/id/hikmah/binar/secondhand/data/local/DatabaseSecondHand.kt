package id.hikmah.binar.secondhand.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.hikmah.binar.secondhand.data.local.dao.AuthDao
import id.hikmah.binar.secondhand.data.local.dao.BuyerDao
import id.hikmah.binar.secondhand.data.local.dao.NotifikasiDao
import id.hikmah.binar.secondhand.data.local.dao.SellerDao
import id.hikmah.binar.secondhand.data.local.entity.*

@Database(
    entities = [Notifikasi::class, BuyerProduct::class, AuthUser::class, ProductSellerEntity::class, SellerOrder::class, BuyerProductDetail::class, SellerCategory::class],
    version = 8
)
abstract class DatabaseSecondHand : RoomDatabase() {
    abstract fun notifikasiDao(): NotifikasiDao
    abstract fun buyerDao(): BuyerDao
    abstract fun sellerDao(): SellerDao
    abstract fun authDao(): AuthDao

    companion object {
        private var INSTANCE: DatabaseSecondHand? = null

        fun getInstance(context: Context): DatabaseSecondHand? {
            if (INSTANCE == null) {
                synchronized(DatabaseSecondHand::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseSecondHand::class.java, "Notifikasi.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }
}