package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.hikmah.binar.secondhand.BuildConfig
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.remote.model.ProductItem
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.ProductRepo
import id.hikmah.binar.secondhand.databinding.FragmentHomeBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.helper.viewModelsFactory
import id.hikmah.binar.secondhand.presentation.adapter.ProductAdapter
import id.hikmah.binar.secondhand.presentation.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.layout_navbar.view.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private val api: ApiService by lazy { ApiClient.instance }
    private val productRepo: ProductRepo by lazy { ProductRepo(api) }
    private val viewModel: ProductViewModel by viewModelsFactory { ProductViewModel(productRepo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moveToNotif()
        moveToJual()
        moveToDaftarJual()
        moveToAkun()

        initRecyclerView()
        observeProduct()
    }

    private fun moveToNotif() {
        binding.footer.footer_notif.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }
    }

    private fun moveToJual() {
        binding.footer.footer_jual.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_detailProdukFragment)
        }
    }

    private fun moveToDaftarJual() {
        binding.footer.footer_daftar_jual.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_saleListFragment)
        }
    }

    private fun moveToAkun() {
        binding.footer.footer_akun.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_akunSayaFragment)
        }
    }

    private fun initRecyclerView() {
        productAdapter = ProductAdapter { id_product, pilem: ProductItem ->
            val bundle = Bundle()
            bundle.putInt("product_id", id_product)
            findNavController().navigate(R.id.action_homeFragment_to_detailProdukFragment, bundle)
        }
        binding.apply {
            rvData.adapter = productAdapter
            rvData.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeProduct() {
        viewModel.getProduct(BuildConfig.ACCESS_TOKEN).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> { binding.pb.isVisible = true}
                Status.SUCCESS -> {
                    binding.pb.isVisible = false
                    productAdapter.updateDataRecycler(it.data)
                }
                Status.ERROR -> {
                    binding.pb.isVisible = false
                    Toast.makeText(requireContext(),
                        "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}