package id.hikmah.binar.secondhand.daftarjual.data.remote.client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SaleListClient {
    private val logging: HttpLoggingInterceptor get() {
        val loggingInterceptor = HttpLoggingInterceptor()
        return loggingInterceptor.apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(logging)
    }.build()

    val instance : SaleListApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://market-final-project.herokuapp.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SaleListApi::class.java)
    }
}