package id.hikmah.binar.secondhand.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SellerCategory(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String? = null
)