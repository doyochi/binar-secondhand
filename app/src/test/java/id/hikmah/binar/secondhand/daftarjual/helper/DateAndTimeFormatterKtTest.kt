package id.hikmah.binar.secondhand.daftarjual.helper

import org.junit.Assert.assertEquals
import org.junit.Test

class DateAndTimeFormatterKtTest {

    @Test
    fun toDateFavorite() {
        assertEquals("10 Jul, 09:32", "2022-07-10T09:32:36.063Z".toDateFavorite())
    }
}