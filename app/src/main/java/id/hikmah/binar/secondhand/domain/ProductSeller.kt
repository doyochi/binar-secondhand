package id.hikmah.binar.secondhand.domain

data class ProductSeller(
    val productImage: String,
    val userId: Int,
    val productDescription: String,
    val location: String,
    val productName: String,
    val productPrice: Int,
    val productCategories: String,
    val productId: Int
)
