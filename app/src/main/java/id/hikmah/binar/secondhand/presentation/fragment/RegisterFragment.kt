package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.hikmah.binar.secondhand.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun btnRegisterClicked() {
        binding.btnRegister.setOnClickListener {
            //add action
        }
    }

    private fun tvLoginClicked(){
        binding.tvLoginInRegister.setOnClickListener {
            //add action
        }
    }


}