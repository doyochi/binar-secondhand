package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.secondhand.data.repository.InfoPenawarRepository
import id.hikmah.binar.secondhand.helper.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InfoPenawarViewModel @Inject constructor(
    private val repository: InfoPenawarRepository
) : ViewModel() {

    fun fetchOrderByProductId(accessToken: String, productId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(repository.fetchSellerOrderByProductId(accessToken, productId)))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        }
    }


    fun fetchOrderById(accessToken: String, id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(repository.fetchProduct(accessToken, id)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
        }
    }

    fun patchOrderById(accessToken: String, id: Int, status: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(repository.patchOrderById(accessToken, id, status)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred from Remote!"))
        }
    }
}