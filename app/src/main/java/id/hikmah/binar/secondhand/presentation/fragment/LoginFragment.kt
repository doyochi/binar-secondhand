package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.data.repository.DatastoreViewModel
import id.hikmah.binar.secondhand.databinding.FragmentLoginBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.presentation.viewmodel.LoginViewModel

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private val dataStore: DatastoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLoginClicked()
        tvRegisterClicked()
        textWatcherEditText()
        backBtnClicked()
    }

    private fun btnLoginClicked() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if(validateEditText(email, password)) {
                viewModel.loginAccount(email, password).observe(viewLifecycleOwner) { result ->

                    when(result.status) {
                        Status.LOADING -> {}

                        Status.SUCCESS -> {
                            Toast.makeText(
                                requireContext(),
                                "Welcome, ${result.data!!.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                            dataStore.saveAccessToken(result.data.accessToken)
                            dataStore.saveLoginState(true)
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        }

                        Status.ERROR -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
    }

    private fun backBtnClicked() {
        binding.btnLoginBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    private fun tvRegisterClicked() {
        binding.tvDaftarDisini.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun validateEditText(email: String, password: String): Boolean {
        return if(!isValidEmail(email) && !isValidPassword(password)) {
            binding.apply {
                tvErrorEmail.text = "Wrong Email"
                tvErrorPassword.text = "Password must be in 6 or more characters"
            }
            false
        } else {
            binding.apply {
                tvErrorEmail.text = ""
                tvErrorPassword.text = ""
            }
            true
        }
    }

    private fun textWatcherEditText() {
        binding.apply {

            etEmail.apply {
                doAfterTextChanged {
                    if(!isValidEmail(text.toString())) {
                        tvErrorEmail.text = "Wrong Email"
                    } else tvErrorEmail.text = ""
                }
            }

            etPassword.apply {
                if(isValidPassword(text.toString())){
                    tvErrorPassword.text = "Password must be in 6 or more characters"
                } else tvErrorPassword.text = ""
            }


        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length >= 6
    }

}