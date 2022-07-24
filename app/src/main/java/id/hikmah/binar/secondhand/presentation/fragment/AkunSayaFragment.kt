package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.UserRepo
import id.hikmah.binar.secondhand.databinding.FragmentAkunSayaBinding
import id.hikmah.binar.secondhand.helper.DatastoreManager
import id.hikmah.binar.secondhand.helper.viewModelsFactory
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.layout_navbar.view.*


class AkunSayaFragment : Fragment() {
    private var _binding : FragmentAkunSayaBinding? = null
    private val binding get() = _binding!!

    private var accessToken : String? = null

    private val api: ApiService by lazy { ApiClient.instance }
    private val userRepo: UserRepo by lazy { UserRepo(api) }
    private val viewModel: UserViewModel by viewModelsFactory { UserViewModel(userRepo) }

    private val pref: DatastoreManager by lazy { DatastoreManager(requireContext()) }
    private val dataStore: DatastoreViewModel by viewModelsFactory { DatastoreViewModel(pref) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAkunSayaBinding.inflate(inflater, container, false)
        validateLogin()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        moveToHome()
        moveNotif()
        moveToJual()
        moveToDaftarJual()
        moveToLengkapiInfoAkun()
        moveToSetting()
        toLogin()
        logOut()
    }

    private fun validateLogin() {

        dataStore.getLoginState().observe(viewLifecycleOwner){
            if (it){
                binding.btnLogin.isVisible = false
            } else {
                binding.apply {
                    tvLogout.isVisible = false
                    ivLogout.isVisible = false
                    lineLogout.isVisible = false
                }

            }
        }
    }

    private fun observeData() {
        dataStore.getAccessToken().observe(viewLifecycleOwner) { token ->
            accessToken = token
            viewModel.getUser(accessToken!!).observe(viewLifecycleOwner) {
                it.data?.let { data ->
                    binding.apply {

                        if (data.image != null) {
                            ibSellerPhoto.setPadding(0,0,0,0)
                            Glide.with(requireContext())
                                .load(data.image)
                                .into(ibSellerPhoto)
                        }

                    }
                }
            }
        }
    }

    private fun logOut(){
        binding.tvLogout.setOnClickListener {
            dataStore.apply {
                saveLoginState(false)
                deleteAllData()
            }
            binding.btnLogin.isVisible = true
        }
    }

    private fun toLogin(){
        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_loginFragment)
        }
    }

    private fun moveToSetting(){
        binding.tvSetting.setOnClickListener {
            findNavController().navigate(R.id.action_akunSayaFragment_to_pengaturanAkunFragment)
        }
    }

    private fun moveToHome() {
        binding.footer.footer_home.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_homeFragment)
        }
    }

    private fun moveNotif() {
        binding.footer.footer_notif.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_notificationFragment)
        }
    }

    private fun moveToJual() {
        binding.footer.footer_jual.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_detailProdukFragment)
        }
    }

    private fun moveToDaftarJual() {
        binding.footer.footer_daftar_jual.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_saleListFragment)
        }
    }

    private fun moveToLengkapiInfoAkun() {
        binding.tvEditAcc.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_lengkapiInfoAkunFragment)
        }
    }

}