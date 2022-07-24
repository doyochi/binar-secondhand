package id.hikmah.binar.secondhand.data.repository

import android.os.Build
import id.hikmah.binar.secondhand.BuildConfig
import id.hikmah.binar.secondhand.data.remote.model.dto.Product
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ProductRepoTest{
    private lateinit var service: ApiService
    private lateinit var repo: ProductRepo

    @Before
    fun setUp(){
        service = mockk()
        repo = ProductRepo(service)
    }

    @Test
    fun getProductTest(): Unit = runBlocking {
        val respAllProducts = mockk<Product>()

        every {
            runBlocking {
                service.getProduct(BuildConfig.ACCESS_TOKEN)
            }
        } returns respAllProducts
        repo.getProduct(BuildConfig.ACCESS_TOKEN)
        verify {
            runBlocking { service.getProduct(BuildConfig.ACCESS_TOKEN) }
        }
    }
}