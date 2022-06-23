package id.hikmah.binar.secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.hikmah.binar.secondhand.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun btnLoginClicked() {
        binding.btnLogin.setOnClickListener {
            //add action
        }
    }

    private fun tvRegisterClicked() {
        binding.tvDaftarDisini.setOnClickListener {
            //add action
        }
    }

}