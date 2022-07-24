package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hikmah.binar.secondhand.data.repository.LoginRepository
import id.hikmah.binar.secondhand.helper.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
): ViewModel() {

    fun loginAccount(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = repository.loginAccount(email, password)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred!"))
        }

    }

}