package id.hikmah.binar.secondhand.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.data.common.viewModelsFactory
import id.hikmah.binar.secondhand.data.repository.DatastoreManager
import id.hikmah.binar.secondhand.data.repository.DatastoreViewModel
import id.hikmah.binar.secondhand.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val pref: DatastoreManager by lazy { DatastoreManager(this) }
    private val datastoreViewModel: DatastoreViewModel by viewModelsFactory { DatastoreViewModel(pref) }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}