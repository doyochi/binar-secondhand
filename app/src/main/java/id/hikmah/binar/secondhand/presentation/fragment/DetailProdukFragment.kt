package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.databinding.FragmentDetailProdukBinding


class DetailProdukFragment : Fragment() {

    private var _binding: FragmentDetailProdukBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailProdukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moveToHome()
    }

    private fun moveToHome() {
        binding.backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_detailProdukFragment_to_homeFragment)
        }
    }



}