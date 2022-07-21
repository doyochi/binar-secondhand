package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.secondhand.data.local.DatabaseSecondHand
import id.hikmah.binar.secondhand.data.repository.SaleListRepository
import id.hikmah.binar.secondhand.helper.Resource
import id.hikmah.binar.secondhand.helper.mapper.toProductSeller
import id.hikmah.binar.secondhand.helper.mapper.toProductSellerEntity
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductSellerDetailsViewModel @Inject constructor(
    private val db: DatabaseSecondHand,
    private val repository: SaleListRepository
) : ViewModel() {

    var stateOfCardClicked = MutableLiveData(1)
        private set

    fun whenProductClicked() {
        stateOfCardClicked.value = 1
    }

    fun whenFavoriteClicked() {
        stateOfCardClicked.value = 2
    }

    fun whenSoldClicked() {
        stateOfCardClicked.value = 3
    }

    private val productDao = db.sellerDao()

    var accessToken = MutableLiveData("")
        private set

    fun fetchUsersDetails(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.fetchUsers(accessToken)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Occurred"))
        }
    }


    fun fetchSoldProduct(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getSoldProduct(accessToken)))
        } catch (e: Exception) {
            emit(Resource.error(null, message = e.message ?: "Error Occurred"))
        }
    }


    fun fetchProductSeller(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        val localData = productDao.getProductSellerDetail()
        emit(Resource.success(data = localData.map { it.toProductSeller() }))

        val shouldLoadFromCache = localData.isNotEmpty()

        if (shouldLoadFromCache) {
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
            productDao.insertProductSellerDetail(
                result.map { it.toProductSellerEntity() }
            )

            emit(Resource.success(
                data = productDao.getProductSellerDetail().map { it.toProductSeller() }
            ))
        }
    }

    fun fetchProductSellerById(accessToken: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = repository.fetchProductSellerDetails(accessToken, id)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
        }
    }
}
