package id.hikmah.binar.secondhand.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hikmah.binar.secondhand.data.local.DatabaseSecondHand
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.*
import id.hikmah.binar.secondhand.helper.DatastoreManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService() : ApiService = ApiClient.instance

    @Provides
    @Singleton
    fun provideRepositoryProductSeller(api: ApiService): SaleListRepository {
        return SaleListRepository(api)
    }

    @Provides
    @Singleton
    fun provideInfoPenawarRepository(api: ApiService) = InfoPenawarRepository(api)

    @Provides
    @Singleton
    fun provideRepositoryRegister(api: ApiService): RegisterRepository {
        return RegisterRepository(api)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(api: ApiService): NotificationRepository {
        return NotificationRepository(api)
    }

    @Provides
    @Singleton
    fun provideRepositoryLogin(api: ApiService): LoginRepository {
        return LoginRepository(api)
    }

    @Provides
    @Singleton
    fun provideTestDatabase(app: Application): DatabaseSecondHand {
        return DatabaseSecondHand.getInstance(app)!!
    }

    @Provides
    @Singleton
    fun provideDataStoreManager(app: Application): DatastoreManager {
        return DatastoreManager(app)
    }
}