package id.hikmah.binar.secondhand.data.local.entity

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

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

@Parcelize
data class PreviewSellerProduct(
    val token: String? = null,
    val productName: String? = null,
    val categoryId: Int? = null,
    val imageUri: Uri? = null,
    val categoryName: String? = null,
    val basePrice: String? = null,
    val productDescription: String? = null,
    val sellerImgUrl: String? = null,
    val sellerName: String? = null,
    val sellerLocation: String? = null
): Parcelable