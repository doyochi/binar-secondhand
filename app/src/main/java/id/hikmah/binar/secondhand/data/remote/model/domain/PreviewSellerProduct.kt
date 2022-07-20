package id.hikmah.binar.secondhand.data.remote.model.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PreviewSellerProduct(
    val token: String? = null,
    val imageUri: Uri? = null,
    val productName: String? = null,
    val categoryId: ArrayList<Int>?,
    val categoryName: ArrayList<String>?,
    val basePrice: String? = null,
    val productDescription: String? = null,
    val sellerImgUrl: String? = null,
    val sellerName: String? = null,
    val sellerLocation: String? = null
) : Parcelable
