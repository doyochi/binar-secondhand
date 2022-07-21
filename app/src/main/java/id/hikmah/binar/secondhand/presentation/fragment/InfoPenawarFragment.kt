package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.databinding.FragmentInfoPenawarBinding
import id.hikmah.binar.secondhand.presentation.viewmodel.InfoPenawarViewModel

@AndroidEntryPoint
class InfoPenawarFragment : Fragment() {
    private var _binding: FragmentInfoPenawarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InfoPenawarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoPenawarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPenawarBack()

//        val productId = InfoPenawarFragmentArgs.fromBundle(arguments as Bundle).orderId

    }

//    private fun fetchProduct(accessToken: String, id: Int) {
//        viewModel.fetchOrderById(accessToken, id).observe(viewLifecycleOwner) { result ->
//            when(result.status) {
//                Status.LOADING -> {}
//
//                Status.SUCCESS -> {
//                    val data = result.data!!
//
//                    Glide.with(requireContext())
//                        .load(data.product.imageUrl)
//                        .into(binding.ivProductImage)
//
//                    binding.tvProductName.text = data.productName
//
//                    binding.tvProductBasePrice.text = "Rp ${data.product.basePrice}"
//
//                    binding.tvProductBidPrice.text = "Rp "
//                }
//
//                Status.ERROR -> {
//                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }

    private fun btnPenawarBack() {
        binding.btnPenawarBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun statusFromClicked(): String {
        return ""
    }


}