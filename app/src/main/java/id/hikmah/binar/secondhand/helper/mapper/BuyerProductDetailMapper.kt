package id.hikmah.binar.secondhand.helper.mapper

import id.hikmah.binar.secondhand.data.common.DomainMapper
import id.hikmah.binar.secondhand.data.local.entity.BuyerProductDetail
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.BuyerProductDtoItem

class BuyerProductDetailMapper : DomainMapper<BuyerProductDtoItem, BuyerProductDetail> {
    override fun mapToDomainModel(modelDto: BuyerProductDtoItem): BuyerProductDetail {
        return BuyerProductDetail(
            id = modelDto.id,
            namaBarang = modelDto.name,
            deskripsiBarang = modelDto.description,
            hargaBarang = modelDto.basePrice,
            imageUrl = modelDto.imageUrl,
            lokasi = modelDto.location,
            kategori = if (modelDto.categories.isNullOrEmpty()) {
                "Kategori"
            } else {
                modelDto.categories[0].name
            },
            imageUser = modelDto.user.imageUrl,
            username = modelDto.user.fullName
        )
    }

    fun toDomainList(initial: List<BuyerProductDtoItem>): List<BuyerProductDetail> {
        return initial.map {
            mapToDomainModel(it)
        }
    }

}