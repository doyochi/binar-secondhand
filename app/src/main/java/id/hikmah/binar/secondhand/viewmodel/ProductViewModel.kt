package id.hikmah.binar.secondhand.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.hikmah.binar.secondhand.BuildConfig
import id.hikmah.binar.secondhand.model.Resource
import id.hikmah.binar.secondhand.repo.ProductRepo
import id.hikmah.binar.secondhand.repo.UserRepo
import kotlinx.coroutines.Dispatchers

class ProductViewModel(private val repo: ProductRepo) : ViewModel() {

    fun getProduct(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.getProduct(BuildConfig.ACCESS_TOKEN)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}