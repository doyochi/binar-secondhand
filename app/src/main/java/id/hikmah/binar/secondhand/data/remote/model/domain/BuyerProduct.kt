package id.hikmah.binar.secondhand.data.remote.model.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BuyerProduct(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "namaBarang") val namaBarang: String? = null,
    @ColumnInfo(name = "hargaBarang") val hargaBarang: Int,
    @ColumnInfo(name = "imageUrl") val imageUrl: String? = null,
    @ColumnInfo(name = "kategori") val kategori: String,
)
