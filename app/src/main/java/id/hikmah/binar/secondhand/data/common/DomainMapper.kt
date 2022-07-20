package id.hikmah.binar.secondhand.data.common

interface DomainMapper<T, domainModel> {
    fun mapToDomainModel(modelDto: T): domainModel
}