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
import id.hikmah.binar.secondhand.databinding.FragmentRegisterBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.presentation.viewmodel.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegisterClicked()
        tvLoginClicked()
        btnRegisterBack()
        textMatcherAfterChanged()
    }

    private fun btnRegisterBack() {
        binding.btnRegisterBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun btnRegisterClicked() {
        binding.btnRegister.setOnClickListener {
            val fullName = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if(validateEditText(fullName, email, password)) {
                viewModel.registerAccount(fullName, email, password).observe(viewLifecycleOwner) { result ->
                    when(result.status) {

                        Status.SUCCESS -> {
                            Toast.makeText(requireContext(), "Register Success!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                        }

                        Status.LOADING -> {}

                        Status.ERROR -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
        }
    }

    private fun validateEditText(name: String, email: String, password: String) : Boolean {
        return if(name.isEmpty() || !isValidEmail(email) || !isValidPassword(password)) {
            binding.tvErrorName.text = "Fill your name"
            binding.tvErrorEmail.text = "Wrong Email"
            binding.tvErrorPassword.text = "Password must be in 6 or more characters"
            false
        } else {
            binding.tvErrorName.text = null
            binding.tvErrorEmail.text = null
            binding.tvErrorPassword.text = null
            true
        }
    }

    private fun tvLoginClicked(){
        binding.tvLoginInRegister.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6 && !TextUtils.isEmpty(password)
    }

    private fun textMatcherAfterChanged() {
        binding.apply {
            etName.apply {
                doAfterTextChanged {
                    if (text.toString().length <= 5) {
                       tvErrorName.text = "Fill your name"
                    } else tvErrorName.text = null
                }
            }

            etEmail.apply {
                doAfterTextChanged {
                    if(!isValidEmail(text.toString())){
                        tvErrorEmail.text = "Wrong Email"
                    } else tvErrorEmail.text = null
                }
            }

            etPassword.apply {
                doAfterTextChanged {
                    if(!isValidPassword(text.toString())){
                        tvErrorPassword.text = "Password must be in 6 or more characters"
                    } else tvErrorPassword.text = null
                }
            }
        }
    }

}