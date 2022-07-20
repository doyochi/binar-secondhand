package id.hikmah.binar.secondhand.data.remote.model.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notifikasi(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "bidPrice") val bidPrice: Int? = null,
    @ColumnInfo(name = "productId") val productId: Int? = null,
    @ColumnInfo(name = "status") val status: String? = null,
    @ColumnInfo(name = "transactionDate") val transactionDate: String? = null,
    @ColumnInfo(name = "updatedAt") val updatedAt: String? = null,
    @ColumnInfo(name = "read") val read: Boolean? = false,
    @ColumnInfo(name = "imageUrl") val imageUrl: String? = null,
    @ColumnInfo(name = "basePrice") val basePrice: Int? = null,
    @ColumnInfo(name = "namaBarang") val namaBarang: String? = null,

    )