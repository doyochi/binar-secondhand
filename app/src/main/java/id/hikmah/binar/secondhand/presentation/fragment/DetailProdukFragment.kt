package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.databinding.FragmentDetailProdukBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.ProductSellerDetailsViewModel

@AndroidEntryPoint
class DetailProdukFragment : Fragment() {

    private var _binding: FragmentDetailProdukBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductSellerDetailsViewModel by viewModels()
    private val dataStore: DatastoreViewModel by viewModels()

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

        val productId = DetailProdukFragmentArgs.fromBundle(arguments as Bundle).productId

        if (productId != 0) {
            fetchProductSellerFromDaftarJual(productId)
        }

    }

    private fun moveToHome() {
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailProdukFragment_to_homeFragment)
        }
    }

    private fun fetchProductSellerFromDaftarJual(productId: Int) {

        dataStore.getAccessToken().observe(viewLifecycleOwner) { token ->
            viewModel.fetchProductSellerById(token, productId)
                .observe(viewLifecycleOwner) { result ->
                    when (result.status) {
                        Status.LOADING -> {}
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        Status.SUCCESS -> {
                            val data = result.data!!

                            binding.namaProdukEt.setText(data.name)

                            binding.hargaProdukEt.setText("${data.basePrice}")

                            binding.kategoriSpinner.prompt = data.categories[0].name

                            binding.deskripsiEt.setText(data.description)

                            Glide.with(requireContext())
                                .load(data.imageUrl)
                                .into(binding.foto)
                        }
                    }
                }
        }
    }


}