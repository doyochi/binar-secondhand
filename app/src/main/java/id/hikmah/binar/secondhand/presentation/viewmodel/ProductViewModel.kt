package id.hikmah.binar.secondhand.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import id.hikmah.binar.secondhand.BuildConfig
import id.hikmah.binar.secondhand.data.remote.model.dto.ProductPostError
import id.hikmah.binar.secondhand.data.repository.ProductRepo
import id.hikmah.binar.secondhand.helper.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.IOException

class ProductViewModel(private val repo: ProductRepo) : ViewModel() {

    fun getProduct(accessToken: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.getProduct(BuildConfig.ACCESS_TOKEN)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun postProduct(access_token: String,
                    name: RequestBody,
                    basePrice: RequestBody,
                    categoryIds: Int,
                    description: RequestBody,
                    location: RequestBody,
                    image: MultipartBody.Part?
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = repo.postProduct(access_token, name, description, basePrice, categoryIds, location, image)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
                Log.d("POST RESPONSE", "Produk berhasil diterbitkan")
            } else {
                val gson = Gson()
                val errorMessage = response.errorBody()?.string()
                val data = gson.fromJson(errorMessage, ProductPostError::class.java)
                response.errorBody()?.close()
                emit(Resource.error(null, data.message))
                Log.d("POST RESPONSE", "Produk gagal diterbitkan")
            }
        } catch (e: HttpException) {
            emit(Resource.error(null, "Something went wrong"))
        } catch (e: IOException) {
            emit(Resource.error(null, "Please check your network connection"))
        } catch (e: Exception) {
            emit(Resource.error(null, "Something went wrong"))
        }
    }

}