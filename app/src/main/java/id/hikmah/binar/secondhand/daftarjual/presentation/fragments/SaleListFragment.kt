package id.hikmah.binar.secondhand.daftarjual.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.daftarjual.helper.Status
import id.hikmah.binar.secondhand.daftarjual.presentation.adapter.FavoriteProductAdapter
import id.hikmah.binar.secondhand.daftarjual.presentation.adapter.ProductListAdapter
import id.hikmah.binar.secondhand.daftarjual.presentation.viewmodel.SaleListViewModel
import id.hikmah.binar.secondhand.databinding.FragmentSaleListBinding
import kotlinx.android.synthetic.main.layout_navbar.view.*

@AndroidEntryPoint
class SaleListFragment : Fragment() {

    private var _binding: FragmentSaleListBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteProductAdapter: FavoriteProductAdapter
    private lateinit var productListAdapter: ProductListAdapter

    private val viewModel: SaleListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSaleListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvScrollFavorite.setOnClickListener {
            initRecyclerViewFavorite()
            submitFavoriteProduct()
        }

        binding.cvScrollProduct.setOnClickListener {
            initRecyclerViewProductSeller()
            submitProductSeller()
        }

        fetchUsers()



        bottomNavBar()
        cardOnClick()
        cvClicked()


    }

    //Info Seller
    private fun fetchUsers(){
        viewModel.fetchUsersDetails().observe(viewLifecycleOwner) {result ->
            when(result.status) {
                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    binding.tvSellerName.text = result.data?.fullName
                    binding.tvSellerCity.text = result.data?.city
                }

                Status.ERROR -> {

                }

            }
        }
    }
    //End


    //Scroll View

    private fun cardOnClick() {
        viewModel.stateOfCardClicked.observe(viewLifecycleOwner) {
            if(it == 1){
                binding.cvScrollProduct.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.PURPLE04))
                binding.ivScrollBox.setImageResource(R.drawable.ic_box_clicked)
                binding.tvScrollProduct.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL01))
                initRecyclerViewProductSeller()
                submitProductSeller()
            } else {
                binding.cvScrollProduct.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.PURPLE01))
                binding.ivScrollBox.setImageResource(R.drawable.ic_box_unclicked)
                binding.tvScrollProduct.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL04))
                }

            if (it == 2) {
                binding.cvScrollFavorite.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.PURPLE04))
                binding.ivScrollFavorite.setImageResource(R.drawable.ic_heart_clicked)
                binding.tvScrollFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL01))
                initRecyclerViewFavorite()
                submitFavoriteProduct()
            } else {
                binding.cvScrollFavorite.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.PURPLE01))
                binding.ivScrollFavorite.setImageResource(R.drawable.ic_heart_unclicked)
                binding.tvScrollFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL04))
            }

            if(it == 3) {
                binding.cvScrollSold.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.PURPLE04))
                binding.ivScrollSold.setImageResource(R.drawable.ic_dollar_sign_clicked)
                binding.tvScrollSold.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL01))
            } else {
                binding.cvScrollSold.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.PURPLE01))
                binding.ivScrollSold.setImageResource(R.drawable.ic_dollar_sign_unclicked)
                binding.tvScrollSold.setTextColor(ContextCompat.getColor(requireContext(), R.color.NEUTRAL04))
            }

        }
    }

    private fun cvClicked() {
        binding.cvScrollProduct.setOnClickListener {
            viewModel.whenProductClicked()
        }
        binding.cvScrollFavorite.setOnClickListener {
            viewModel.whenFavoriteClicked()
        }
        binding.cvScrollSold.setOnClickListener {
            viewModel.whenSoldClicked()
        }
    }

    //End

    //RecyclerView

    //Favorite
    private fun initRecyclerViewFavorite() {
        binding.rvSaleList.apply {
            favoriteProductAdapter = FavoriteProductAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteProductAdapter
        }
    }

    private fun submitFavoriteProduct() {
        viewModel.fetchFavoriteProduct().observe(viewLifecycleOwner) { resource ->

            when(resource.status) {

                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    favoriteProductAdapter.submitData(resource.data!!)
                }

                Status.ERROR -> {

                }

            }

        }
    }

    //Product
    private fun initRecyclerViewProductSeller() {
        binding.rvSaleList.apply {
            productListAdapter = ProductListAdapter()
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productListAdapter
        }
    }

    private fun submitProductSeller() {
        viewModel.fetchProductSeller().observe(viewLifecycleOwner) {result ->
            when(result.status){

                Status.LOADING -> {

                }

                Status.SUCCESS -> {
                    productListAdapter.submitListProduct(result.data!!)
                }

                Status.ERROR -> {

                }

            }

        }
    }
    //End


    //Footer
    private fun bottomNavBar(){
        moveToDaftarJual()
        moveToJual()
        moveToNotif()
        moveToHome()
        moveToAkun()
    }

    private fun moveToHome() {
        binding.footer.footer_home.setOnClickListener{
            findNavController().navigate(R.id.action_saleListFragment_to_homeFragment)
        }
    }

    private fun moveToNotif() {
        binding.footer.footer_home.setOnClickListener{
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
        binding.footer.footer_akun.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_akunSayaFragment)
        }
    }
}