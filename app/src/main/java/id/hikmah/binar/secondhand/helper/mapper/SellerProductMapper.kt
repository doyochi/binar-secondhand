package id.hikmah.binar.secondhand.helper.mapper

import id.hikmah.binar.secondhand.data.common.DomainMapper
import id.hikmah.binar.secondhand.data.local.entity.ProductSellerEntity
import id.hikmah.binar.secondhand.data.remote.model.dto.ProductSellerDto

class SellerProductMapper: DomainMapper<ProductSellerDto, ProductSellerEntity> {
    override fun mapToDomainModel(modelDto: ProductSellerDto): ProductSellerEntity {
        return ProductSellerEntity(
            id = modelDto.id,
            userId = modelDto.userId,
            productName = modelDto.name,
            productDescription = modelDto.description,
            productPrice = modelDto.basePrice,
            productImage = modelDto.imageUrl,
            location = modelDto.location,
//            st = modelDto.status,
            productCategory = modelDto.categories.joinToString { it.name }
        )
    }

    fun toDomainList(initial: List<ProductSellerDto>): List<ProductSellerEntity> {
        return initial.map {
            mapToDomainModel(it)
        }
    }
}