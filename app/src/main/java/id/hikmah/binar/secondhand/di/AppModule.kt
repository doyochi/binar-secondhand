package id.hikmah.binar.secondhand.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hikmah.binar.secondhand.data.local.LocalDatabase
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.InfoPenawarRepository
import id.hikmah.binar.secondhand.data.repository.LoginRepository
import id.hikmah.binar.secondhand.data.repository.RegisterRepository
import id.hikmah.binar.secondhand.data.repository.SaleListRepository
import id.hikmah.binar.secondhand.helper.Authenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService() : ApiService = ApiClient.instance

    @Provides
    @Singleton
    fun provideRepositoryProductSeller(api: ApiService, authenticator: Authenticator) : SaleListRepository {
        return SaleListRepository(api, authenticator)
    }

    @Provides
    @Singleton
    fun provideAuthenticator(app: Application) = Authenticator(app)

    @Provides
    @Singleton
    fun provideInfoPenawarRepository(api: ApiService) = InfoPenawarRepository(api)

    @Provides
    @Singleton
    fun provideRepositoryRegister(api: ApiService) : RegisterRepository {
        return RegisterRepository(api)
    }

    @Provides
    @Singleton
    fun provideRepositoryLogin(api: ApiService, authenticator: Authenticator) : LoginRepository {
        return LoginRepository(api, authenticator)
    }

    @Provides
    @Singleton
    fun provideTestDatabase(app: Application) : LocalDatabase {
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "test.db"
        ).build()
    }
}