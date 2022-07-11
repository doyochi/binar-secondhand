package id.hikmah.binar.secondhand.daftarjual.helper

import java.text.SimpleDateFormat
import java.util.*

fun String.toDateFavorite(): String? {
    if (this.isEmpty()) {
        return "-"
    }

    val inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    val outputPattern = "dd MMM, HH:mm"

    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outputPattern, Locale("in"))

    val inputDate = inputFormat.parse(this)

    return inputDate?.let {
        outputFormat.format(it)
    }
}