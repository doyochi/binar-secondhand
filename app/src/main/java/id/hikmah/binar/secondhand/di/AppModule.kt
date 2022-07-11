package id.hikmah.binar.secondhand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.SaleListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService() : ApiService = ApiClient.instance

    @Provides
    @Singleton
    fun provideRepositoryProductSeller(api: ApiService) : SaleListRepository {
        return SaleListRepository(api)
    }
}