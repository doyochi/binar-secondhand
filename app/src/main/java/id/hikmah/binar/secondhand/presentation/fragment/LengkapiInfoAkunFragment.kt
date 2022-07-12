package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.UserRepo
import id.hikmah.binar.secondhand.databinding.FragmentLengkapiInfoAkunBinding
import id.hikmah.binar.secondhand.helper.viewModelsFactory
import id.hikmah.binar.secondhand.presentation.viewmodel.UserViewModel


class LengkapiInfoAkunFragment : Fragment() {
    private var _binding: FragmentLengkapiInfoAkunBinding ?= null
    private val binding get() = _binding!!

    private val api: ApiService by lazy { ApiClient.instance }
    private val userRepo: UserRepo by lazy { UserRepo(api) }
    private val viewModel: UserViewModel by viewModelsFactory { UserViewModel(userRepo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLengkapiInfoAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData(){
        viewModel.getUser().observe(viewLifecycleOwner){
            if (it != null){
                binding.apply {
                    namaEt.setText(it.data?.fullName)
                    alamatEt.setText(it.data?.address)
                    nomorHpEt.setText(it.data?.phoneNumber)
                }
            }
        }
    }

//    private fun updateProfile(){
//        binding.apply {
//            simpanProfileBtn.setOnClickListener{
//                val username = namaEt.text.toString()
//                val alamat = alamatEt.text.toString()
//                val nomorHp = nomorHpEt.text.toString()
//
//                val user =User(id,username,nomorHp,alamat,"","")
//                viewModel.
//            }
//        }
//    }
}