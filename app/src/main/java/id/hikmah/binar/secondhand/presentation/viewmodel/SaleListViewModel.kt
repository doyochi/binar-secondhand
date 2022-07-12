package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.secondhand.data.repository.SaleListRepository
import id.hikmah.binar.secondhand.helper.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SaleListViewModel @Inject constructor(
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

        fun fetchFavoriteProduct(accessToken: String) = liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(data = repository.getFavoriteProduct(accessToken)))
            } catch (e: Exception) {
                emit(Resource.error(null, message = e.message ?: "Error Occurred"))
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

        try {
            emit(Resource.success(data = repository.fetchProductSeller(accessToken)))
        } catch (e: Exception) {
            emit(Resource.error(null, e.message ?: "Error Occurred"))
        }
    }
}