package id.hikmah.binar.secondhand.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductSellerEntity(
    val productImage: String,
    val productName: String,
    val productCategory: String,
    val productPrice: Int,
    @PrimaryKey val id: Int
)