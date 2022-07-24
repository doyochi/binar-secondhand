package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.hikmah.binar.secondhand.data.repository.UserRepo
import id.hikmah.binar.secondhand.helper.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserViewModel(private val repo: UserRepo) : ViewModel() {

    fun getUser(access_token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.getUser(access_token)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun editUser(access_token: String,
                 fullName: RequestBody?,
                 phoneNumber: RequestBody?,
                 address: RequestBody?,
                 city: RequestBody?,
                 image: MultipartBody.Part?
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = repo.editUser(access_token, fullName, phoneNumber, address, city, image)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun changePasswordUser(access_token: String,
                           oldPass: RequestBody,
                           newPass: RequestBody,
                           newPassConfirm: RequestBody
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = repo.changePasswordUser(access_token, oldPass, newPass, newPassConfirm)
            emit(Resource.success(response))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


}