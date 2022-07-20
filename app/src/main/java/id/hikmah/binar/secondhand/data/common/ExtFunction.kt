package id.hikmah.binar.secondhand.data.common

import android.content.Context
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun Int.toRp() : String{
    val locale = Locale("in","ID")
    val numberFormat = NumberFormat.getCurrencyInstance(locale)
    numberFormat.maximumFractionDigits = 0
    return  numberFormat.format(this)

}

fun String.toFormatPhone() : String{

    val result = if(this.subSequence(0,2) == "08"){
        val tes = this.subSequence(0,2)
        this.replace(tes.toString(),"628")
    }else{
      this.replace("+","")
    }


    return result
}


fun String.toFormatDate() : String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM,HH:mm", Locale.getDefault())


    val inputDate = inputFormat.parse(this)
    inputDate.let {
        return outputFormat.format(it!!)
    }
}

//For fragment
fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireView().windowToken, 0)
}

////For Activity
//fun AppCompatActivity.hideKeyboard() {
//    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//    imm.hideSoftInputFromWindow(this.window.attributes.token, 0)
//}

//search on keyboard done
fun EditText.onDone(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            callback.invoke()
        }
            false

    }
}

inline fun <reified T : ViewModel> ComponentActivity.viewModelsFactory(crossinline viewModelInitialization: () -> T): Lazy<T> {
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelInitialization.invoke() as T
            }
        }
    }
}

inline fun <reified T : ViewModel> Fragment.viewModelsFactory(crossinline viewModelInitialization: () -> T): Lazy<T> {
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelInitialization.invoke() as T
            }
        }
    }
}