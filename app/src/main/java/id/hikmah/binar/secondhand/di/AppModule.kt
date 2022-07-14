package id.hikmah.binar.secondhand.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hikmah.binar.secondhand.data.local.TestDatabase
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.LoginRepository
import id.hikmah.binar.secondhand.data.repository.RegisterRepository
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

    @Provides
    @Singleton
    fun provideRepositoryRegister(api: ApiService) : RegisterRepository {
        return RegisterRepository(api)
    }

    @Provides
    @Singleton
    fun provideRepositoryLogin(api: ApiService) : LoginRepository {
        return LoginRepository(api)
    }

    @Provides
    @Singleton
    fun provideTestDatabase(app: Application) : TestDatabase {
        return Room.databaseBuilder(
            app,
            TestDatabase::class.java,
            "test.db"
        ).build()
    }
}