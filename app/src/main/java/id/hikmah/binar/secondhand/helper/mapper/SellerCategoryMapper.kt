package id.hikmah.binar.secondhand.helper.mapper

import id.hikmah.binar.secondhand.data.common.DomainMapper
import id.hikmah.binar.secondhand.data.remote.model.dto.Category
import id.hikmah.binar.secondhand.data.remote.model.dto.CategoryItem

class SellerCategoryMapper: DomainMapper<CategoryItem, Category> {
    override fun mapToDomainModel(modelDto: CategoryItem): Category {
        return Category(
            id = modelDto.id,
            name = modelDto.name
        )
    }

    fun toDomainList(initial: List<CategoryItem>): List<Category> {
        return initial.map {
            mapToDomainModel(it)
        }
    }
}