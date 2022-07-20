package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.toRp
import id.hikmah.binar.secondhand.data.local.DatabaseSecondHand
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.repository.BuyerRepo
import id.hikmah.binar.secondhand.databinding.FragmentBuyer6Binding
import id.hikmah.binar.secondhand.helper.mapper.BuyerProductDetailMapper
import id.hikmah.binar.secondhand.presentation.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_buyer6.*
import me.relex.circleindicator.CircleIndicator3

class Buyer6 : Fragment() {
    private var _binding: FragmentBuyer6Binding? = null
    private val binding get() = _binding!!

    private var imagesList = mutableListOf<Int>()
    lateinit var textView: TextView
    private var productId : Int? = null

    private val buyerRepo : BuyerRepo by lazy { BuyerRepo(
        ApiClient.instanceBuyer,
        BuyerProductDetailMapper(), DatabaseSecondHand.getInstance(requireContext())!!
    ) }
    private val viewModel : Buyer6ViewModel by lazy { Buyer6ViewModel(buyerRepo) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBuyer6Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        productId?.let { getData(it) }
        postToList()

        textView = binding.deskripsi.findViewById(R.id.deskripsi)
        val text: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        textView.text = text
        textView.movementMethod = ScrollingMovementMethod()

        view_pager2.adapter = ViewPagerAdapter(imagesList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val circleIndicator = binding.circleIndicator.findViewById<CircleIndicator3>(R.id.circle_indicator)
        circleIndicator.setViewPager(view_pager2)

        val bottomSheetFragment = BottomSheetFragment{
        }
        binding.btnTertarik.setOnClickListener {
            bottomSheetFragment.show(parentFragmentManager, "BottomSheetDialog")
        }
    }

    private fun getData(id: Int){
        viewModel.getProductDetail(id).observe(viewLifecycleOwner){
            it.data.let { data ->
                Glide.with(requireContext()).load(data?.imageUrl).into(binding.ivProduk)
                Glide.with(requireContext()).load(data?.imageUser).into(binding.ivSeller)
                Glide.with(requireContext()).load(data?.imageUrl).into(binding.ivProduk)
                Glide.with(requireContext()).load(data?.imageUser).into(binding.ivSeller)
                binding.tvKota.text = data?.lokasi
                binding.tvNamaBarang.text = data?.namaBarang
                binding.tvHarga.text = data?.hargaBarang?.toRp()
                binding.deskripsi.text = data?.deskripsiBarang
                binding.tvKategori.text = data?.kategori
                binding.tvNamaPenjual.text = data?.username
            }
        }
    }


    private fun addToList(image: Int){
        imagesList.add(image)
    }

    private fun postToList(){
        for (i in 1..5){
            addToList(R.drawable.background_jam)
        }
    }

}