package id.hikmah.binar.secondhand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hikmah.binar.secondhand.daftarjual.data.remote.client.SaleListApi
import id.hikmah.binar.secondhand.daftarjual.data.repository.SaleListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): SaleListApi {
        return ApiClient.instance
    }

    @Provides
    @Singleton
    fun provideRepositoryProductSeller(api: SaleListApi) : SaleListRepository {
        return SaleListRepository(api)
    }
}