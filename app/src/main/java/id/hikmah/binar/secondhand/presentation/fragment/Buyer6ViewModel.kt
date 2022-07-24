package id.hikmah.binar.secondhand.presentation.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.ApiError
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.PostBuyerOrderBody
import id.hikmah.binar.secondhand.data.repository.BuyerRepo
import id.hikmah.binar.secondhand.helper.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException


class Buyer6ViewModel(private val buyerRepo: BuyerRepo) : ViewModel() {

    fun getProductDetail(id: Int) = buyerRepo.getProductDetail(id).asLiveData()

    fun setBuyerOrder(postBuyerOrderBody: PostBuyerOrderBody,accessToken : String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            val response = buyerRepo.setBuyerOrder(postBuyerOrderBody,accessToken)
            if(response.isSuccessful){
                emit(Resource.success(response.body()))
            }else{
                val gson = Gson()
                val errorMsg = response.errorBody()?.string()
                val data = gson.fromJson(errorMsg,ApiError::class.java)
                response.errorBody()?.close()
                emit(Resource.error(null, data.message.toString()))
            }
        } catch (ex : HttpException){
            emit(Resource.error(null,ex.message() ?: "Something went wrong"))
        } catch (ex : IOException){
            emit(Resource.error(null,"Please check your network connection"))
        } catch (ex : Exception){
            emit(Resource.error(null,"Something went wrong!"))
        }
    }
}