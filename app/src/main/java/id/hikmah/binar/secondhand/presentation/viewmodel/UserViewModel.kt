package id.hikmah.binar.secondhand.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.hikmah.binar.secondhand.data.repository.UserRepo
import id.hikmah.binar.secondhand.helper.Resource
import kotlinx.coroutines.Dispatchers

class UserViewModel(private val repo: UserRepo) : ViewModel() {

    fun getUser() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repo.getUser("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImxvbGlwb3BAZ21haWwuY29tIiwiaWF0IjoxNjU2Njc1Mjk4fQ.XJ-iifm6kdlVd4aTBv3KGtCeE7jiq51CzeKp1z3SsvQ")))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

//    fun putUser() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(repo.putUser(,))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }

}