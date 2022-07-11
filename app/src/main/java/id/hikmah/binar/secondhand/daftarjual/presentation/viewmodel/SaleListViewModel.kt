package id.hikmah.binar.secondhand.daftarjual.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.secondhand.daftarjual.data.repository.SaleListRepository
import id.hikmah.binar.secondhand.daftarjual.helper.Resource
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

        fun fetchFavoriteProduct() = liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(data = repository.getFavoriteProduct()))
            } catch (e: Exception) {
                emit(Resource.error(message = e.message ?: "Error Occurred"))
            }
        }

    fun fetchUsersDetails() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.fetchUsers()))
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error Occurred"))
        }
    }

    fun fetchProductSeller() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = repository.fetchProductSeller()))
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Error Occurred"))
        }
    }
}