package id.hikmah.binar.secondhand.helper

import java.text.NumberFormat
import java.util.*

class Converter {
    companion object {
        fun rupiah(number: Int): String {
            val localeID = Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            return numberFormat.format(number).toString()
        }
    }
}