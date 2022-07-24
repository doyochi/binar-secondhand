package id.hikmah.binar.secondhand.helper.mapper

import id.hikmah.binar.secondhand.data.local.entity.ProductSellerEntity
import id.hikmah.binar.secondhand.data.remote.model.dto.ProductSellerDto
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
        productDescription = this.description,
        location = this.location,
        userId = this.userId,
        id = this.id
    )
}

fun ProductSellerDto.toProductSeller(): ProductSeller {
    var categories = ""

    if (this.categories != null) {
        val sizeOfCategory = this.categories.indices
        val lastIndexOfCategory = sizeOfCategory.last

        for (i in sizeOfCategory) {
            categories += if (i == lastIndexOfCategory) {
                this.categories[i].name
            } else {
                this.categories[i].name + ", "
            }
        }
    } else {
        categories = ""
    }
    return ProductSeller(
        productImage = this.imageUrl,
        productName = this.name,
        productId = this.id,
        productDescription = this.description,
        productCategories = categories,
        productPrice = this.basePrice,
        userId = this.userId,
        location = this.location
    )
}

fun ProductSellerEntity.toProductSeller(): ProductSeller {
    return ProductSeller(
        productDescription = this.productDescription,
        userId = this.userId,
        location = this.location,
        productImage = this.productImage,
        productName = this.productName,
        productPrice = this.productPrice,
        productCategories = this.productCategory,
        productId = this.id
    )
}