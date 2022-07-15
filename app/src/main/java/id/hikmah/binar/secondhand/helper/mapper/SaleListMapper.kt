package id.hikmah.binar.secondhand.helper.mapper

import id.hikmah.binar.secondhand.data.local.FavoriteProductEntity
import id.hikmah.binar.secondhand.data.local.ProductSellerEntity
import id.hikmah.binar.secondhand.data.remote.model.ProductSellerDto
import id.hikmah.binar.secondhand.data.remote.model.sellerorder.SellerOrderDto
import id.hikmah.binar.secondhand.domain.FavoriteProduct
import id.hikmah.binar.secondhand.domain.ProductSeller

fun ProductSellerDto.toProductSellerEntity() : ProductSellerEntity {

    var categories = ""

    if(this.categories != null) {
        val sizeOfCategory = this.categories.indices
        val lastIndexOfCategory = sizeOfCategory.last

        for (i in sizeOfCategory) {
            categories += if(i == lastIndexOfCategory) {
                this.categories[i].name
            } else {
                this.categories[i].name + ", "
            }
        }
    } else {
        categories = ""
    }


    return ProductSellerEntity(
        productImage = this.imageUrl,
        productName = this.name,
        productCategory = categories,
        productPrice = this.basePrice,
        id = this.id
    )
}

fun ProductSellerEntity.toProductSeller() : ProductSeller {
    return ProductSeller(
        productImage = this.productImage,
        productName = this.productName,
        productPrice = this.productPrice,
        productCategories = this.productCategory,
        productId = this.id
    )
}

fun SellerOrderDto.toFavoriteProductEntity() : FavoriteProductEntity {
    return FavoriteProductEntity(
        productImage = this.product.imageUrl,
        productName = this.productName,
        productPrice = this.product.basePrice,
        productBidPrice = this.price,
        id = this.id,
        productTransactionDate = this.transactionDate,
        buyerId = this.buyerId
    )
}

fun FavoriteProductEntity.toFavoriteProduct() : FavoriteProduct {
    return FavoriteProduct(
        productName = this.productName,
        productImage = this.productImage,
        productTransactionDate = this.productTransactionDate,
        productBidPrice = this.productBidPrice,
        productPrice = this.productPrice,
        id = this.id,
        buyerId = this.buyerId
    )
}

fun FavoriteProduct.toFavoriteProductEntity() : FavoriteProductEntity {
    return FavoriteProductEntity(
        productName = this.productName,
        productImage = this.productImage,
        productPrice = this.productPrice,
        productBidPrice = this.productBidPrice,
        productTransactionDate = this.productTransactionDate,
        id = this.id,
        buyerId = this.buyerId
    )
}