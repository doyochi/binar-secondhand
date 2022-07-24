package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.hideLoading
import id.hikmah.binar.secondhand.data.common.showLoading
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.UserRepo
import id.hikmah.binar.secondhand.databinding.FragmentPengaturanAkunBinding
import id.hikmah.binar.secondhand.helper.DatastoreManager
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.helper.viewModelsFactory
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.UserViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class PengaturanAkunFragment : Fragment() {

    private var _binding: FragmentPengaturanAkunBinding? = null
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
        _binding = FragmentPengaturanAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePassword()
        onButtonIsPressed()
    }

    private fun changePassword() {
        binding.btnChangePass.setOnClickListener {
            val passLama = binding.etPassword.text.toString()
            val passBaru = binding.etPasswordBaru.text.toString()
            val passBaruConfirm = binding.etPasswordConfirm.text.toString()

            if (passwordValidation(passLama, passBaru, passBaruConfirm)) {
                val oldPass = passLama.toRequestBody("current_password".toMediaTypeOrNull())
                val newPass = passBaru.toRequestBody("new_password".toMediaTypeOrNull())
                val newPassConfirm = passBaruConfirm.toRequestBody("confirm_password".toMediaTypeOrNull())

                dataStore.getAccessToken().observe(viewLifecycleOwner) { token ->
                    viewModel.changePasswordUser(token, oldPass, newPass, newPassConfirm).observe(viewLifecycleOwner) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                hideLoading()
                                binding.apply {
                                    // Clear Text
                                    etPassword.text?.clear()
                                    etPasswordBaru.text?.clear()
                                    etPasswordConfirm.text?.clear()
                                    // Clear Focus
                                    etPassword.text?.clearSpans()
                                    etPasswordBaru.text?.clearSpans()
                                    etPasswordConfirm.text?.clearSpans()
                                }
                            }
                            Status.ERROR -> {
                                hideLoading()
                            }
                            Status.LOADING -> {
                                // Munculkan LoadingDialog
                                showLoading(requireActivity())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun passwordValidation(old_pass: String, new_pass: String, confirm_password: String): Boolean {
        var result = true
        if (old_pass.isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong!"
            result = false
        }

        if (new_pass.isEmpty()) {
            binding.etPasswordBaru.error = "Password tidak boleh kosong!"
            result = false
        } else if (new_pass.length < 6) {
            binding.etPasswordBaru.error = "Password minimum 6 Karakter!"
            result = false
        }

        if (confirm_password.isEmpty()) {
            binding.etPasswordConfirm.error = "Konfirmasi Password tidak boleh kosong!"
            result = false
        } else if (new_pass != confirm_password) {
            binding.etPasswordConfirm.error = "Password harus sama!"
            binding.etPasswordConfirm.error = "Password harus sama!"
            result = false
        }
        return result
    }

    private fun onButtonIsPressed() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
