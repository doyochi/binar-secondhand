package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.secondhand.data.local.LocalDatabase
import id.hikmah.binar.secondhand.data.repository.SaleListRepository
import id.hikmah.binar.secondhand.helper.Resource
import id.hikmah.binar.secondhand.helper.mapper.toFavoriteProduct
import id.hikmah.binar.secondhand.helper.mapper.toFavoriteProductEntity
import id.hikmah.binar.secondhand.helper.mapper.toProductSeller
import id.hikmah.binar.secondhand.helper.mapper.toProductSellerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SaleListViewModel @Inject constructor(
    private val db: LocalDatabase,
    private val repository: SaleListRepository
    ) : ViewModel() {

    var stateOfCardClicked = MutableLiveData(1)
        private set

    fun whenProductClicked(){
        stateOfCardClicked.value = 1
    }

    fun whenFavoriteClicked() {
        stateOfCardClicked.value = 2
    }

    fun whenSoldClicked() {
        stateOfCardClicked.value = 3
    }

    private val favoriteDao = db.favoriteProductDao()
    private val productDao = db.productSellerDao()

    var accessToken = MutableLiveData("")
        private set

    fun getAccessToken() {
        viewModelScope.launch {
            repository.getAccessToken()
                .collect {
                    accessToken.postValue(it)
                }
        }
    }

    fun fetchFavoriteProduct(
        accessToken: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        val localData = favoriteDao.getFavoriteProductFromEntity()
        emit(Resource.success(data = localData.map { it.toFavoriteProduct() }))

        val shouldLoadFromCache = localData.isNotEmpty()

        if(shouldLoadFromCache) {
            return@liveData
        }

        val remoteData = try {
            val response = repository.fetchFavoriteProduct(accessToken)
            response
        } catch (e: IOException) {
            emit(Resource.error(null, message = e.message ?: "Error Occurred!"))
            null
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
            null
        }

        remoteData?.let { result ->
            favoriteDao.insertFavoriteProductToEntity(
                result.map { it.toFavoriteProductEntity() }
            )

            emit(Resource.success(
                data = favoriteDao.getFavoriteProductFromEntity().map { it.toFavoriteProduct() }
            ))
        }
    }

    fun fetchUsersDetails(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.fetchUsers(accessToken)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Occurred"))
        }
    }

    fun fetchProductSeller(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        val localData = productDao.getProductSellerEntity()
        emit(Resource.success(data = localData.map { it.toProductSeller() }))

        val shouldLoadFromCache = localData.isNotEmpty()

        if(shouldLoadFromCache) {
            return@liveData
        }

        val remoteData = try {
            val response = repository.fetchProductSeller(accessToken)
            response
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
            null
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error from Remote!"))
            null
        }

        remoteData?.let { result ->
            productDao.insertProductSellerToEntity(
                result.map { it.toProductSellerEntity() }
            )

            emit(Resource.success(
                data = productDao.getProductSellerEntity().map { it.toProductSeller() }
            ))
        }

    }
}
