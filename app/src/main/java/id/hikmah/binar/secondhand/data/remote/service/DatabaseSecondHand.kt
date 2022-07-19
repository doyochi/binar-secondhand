package id.hikmah.binar.secondhand.data.remote.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.and2t2.secondhand.data.local.AuthDao
import com.and2t2.secondhand.data.local.BuyerDao
import com.and2t2.secondhand.data.local.NotifikasiDao
import id.hikmah.binar.secondhand.data.local.ProductSellerEntity
import id.hikmah.binar.secondhand.data.remote.dao.SellerDao
import id.hikmah.binar.secondhand.data.remote.model.domain.*

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