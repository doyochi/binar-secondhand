package id.hikmah.binar.secondhand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.hikmah.binar.secondhand.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_akun)

//        val arrayAdapter: ArrayAdapter<*>
//        val arrayList = arrayListOf<String>("Brand New In Box","Brand New Open Box","Second")
//        val spinner: Spinner =findViewById(R.id.kategori_spinner)
//
//        arrayAdapter = ArrayAdapter(
//            this,
//            R.layout.color_spinner, arrayList
//        ).also { arrayAdapter ->
//            arrayAdapter.setDropDownViewResource(R.layout.color_spinner_list)
//        }
//        spinner.adapter = arrayAdapter
    }
}