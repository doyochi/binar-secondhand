package id.hikmah.binar.secondhand.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductSellerEntity(
    val productDescription: String,
    val location: String,
    val userId: Int,
    val productImage: String,
    val productName: String,
    val productCategory: String,
    val productPrice: Int,
    @PrimaryKey val id: Int
)