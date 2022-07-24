package id.hikmah.binar.secondhand.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.hideLoading
import id.hikmah.binar.secondhand.data.common.showLoading
import id.hikmah.binar.secondhand.data.common.showSnackbar
import id.hikmah.binar.secondhand.data.common.toRp
import id.hikmah.binar.secondhand.data.local.DatabaseSecondHand
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.PostBuyerOrderBody
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.repository.BuyerRepo
import id.hikmah.binar.secondhand.databinding.BottomSheetBinding
import id.hikmah.binar.secondhand.databinding.FragmentBuyer6Binding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.helper.mapper.BuyerProductDetailMapper
import id.hikmah.binar.secondhand.presentation.activity.MainActivity
import id.hikmah.binar.secondhand.presentation.adapter.ViewPagerAdapter
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import kotlinx.android.synthetic.main.fragment_buyer6.*
import me.relex.circleindicator.CircleIndicator3
import org.koin.androidx.viewmodel.ext.android.viewModel

class Buyer6 : Fragment() {
    private var _binding: FragmentBuyer6Binding? = null
    private val binding get() = _binding!!

    private var imagesList = mutableListOf<Int>()
    lateinit var textView: TextView
    private var productId : Int? = null
    private lateinit var dataHarga : PostBuyerOrderBody
    private val dataStore : DatastoreViewModel by viewModel()

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

        productId = arguments?.getInt("product_key")!!
        productId?.let { getData(it) }
        postToList()
        onBackPressed()

        textView = binding.deskripsi.findViewById(R.id.deskripsi)
        val text: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        textView.text = text
        textView.movementMethod = ScrollingMovementMethod()

        view_pager2.adapter = ViewPagerAdapter(imagesList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val circleIndicator = binding.circleIndicator.findViewById<CircleIndicator3>(R.id.circle_indicator)
        circleIndicator.setViewPager(view_pager2)

        binding.btnTertarik.setOnClickListener {
            bottomSheet()
        }
    }

//    private fun showAlertDialogWithAction() {
//        val dialog = AlertDialog.Builder(requireContext())
//        dialog.setMessage("Anda harus login terlebih dahulu")
//        dialog.setPositiveButton("Login") { _, _ ->
//            startActivity(Intent(requireActivity(), MainActivity::class.java))
//        }
//        dialog.setNegativeButton("Batal") { _, _ ->
//
//        }
//        dialog.setCancelable(false)
//        dialog.show()
//    }

    private fun getData(id: Int){
        viewModel.getProductDetail(id).observe(viewLifecycleOwner){
            it.data.let { data ->
                Glide.with(requireContext()).load(data?.imageUrl).into(binding.ivProduk)
                Glide.with(requireContext()).load(data?.imageUser).into(binding.ivSeller)
                binding.tvKota.text = data?.lokasi
                binding.tvNamaBarang.text = data?.namaBarang
                binding.tvHarga.text = data?.hargaBarang?.toRp()
                binding.deskripsi.text = data?.deskripsiBarang
                binding.tvKategori.text = data?.kategori
                binding.tvNamaPenjual.text = data?.username
            }
            val isTrue: Boolean = it.data?.id == null
            binding.pbLoading.isVisible =  isTrue
        }
    }

    private fun bottomSheet(){
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        dialog.setContentView(view)
        dialog.show()

        val ivBarang = view.findViewById<ShapeableImageView>(R.id.iv_item_bid)
        val namaBarang = view.findViewById<TextView>(R.id.tv_name_item_bid)
        val hargaBarang = view.findViewById<TextView>(R.id.tv_price_item_bid)
        val etHarga = view.findViewById<TextInputLayout>(R.id.et_harga)
        val btnKirim = view.findViewById<MaterialButton>(R.id.btn_kirim)

        viewModel.getProductDetail(productId!!).observe(viewLifecycleOwner){
            it.data?.let { data ->
                Glide.with(requireContext()).load(data.imageUrl)
                    .into(ivBarang)
                namaBarang.text = data.namaBarang
                hargaBarang.text = data.hargaBarang.toRp()
            }
        }

        btnKirim.setOnClickListener{
            val price = etHarga.editText?.text.toString()
            if (price == ""){
                Toast.makeText(requireContext(),"Masukkan Harga Tawar Anda", Toast.LENGTH_SHORT).show()
            }else{
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

    private fun onBackPressed(){
        binding.fabBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}