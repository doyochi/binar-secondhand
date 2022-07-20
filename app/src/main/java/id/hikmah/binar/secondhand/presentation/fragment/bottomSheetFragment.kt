package id.hikmah.binar.secondhand.presentation.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.*
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.PostBuyerOrderBody
import id.hikmah.binar.secondhand.data.repository.DatastoreViewModel
import id.hikmah.binar.secondhand.databinding.BottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFragment(private val onClicked : (price : String) -> Unit) : BottomSheetDialogFragment(){

    private var _binding: BottomSheetBinding? =null
    private val binding get() = _binding!!

    private var productId : Int? = null
    private lateinit var dataHarga : PostBuyerOrderBody
    private val viewModel : Buyer6ViewModel by viewModel()
    private val dataStore : DatastoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val inflater = LayoutInflater.from(context)
        val etHarga = view.findViewById<TextInputLayout>(R.id.et_harga)
        val ivBarang = view.findViewById<ShapeableImageView>(R.id.iv_item_bid)
        val btnTertarik = view.findViewById<Button>(R.id.btn_tertarik)
        val btnTertarikSuccess = view.findViewById<Button>(R.id.btn_tertarik_success)
        val namaBarang = view.findViewById<TextView>(R.id.tv_name_item_bid)
        val hargaBarang = view.findViewById<TextView>(R.id.tv_price_item_bid)

//        viewModel.getProductDetail(productId!!).observe(viewLifecycleOwner){
//            it.data?.let { data ->
//                Glide.with(requireContext()).load(data.imageUrl)
//                    .into(ivBarang)
//                namaBarang.text = data.namaBarang
//                hargaBarang.text = data.hargaBarang.toRp()
//            }
//        }

        _binding = BottomSheetBinding.inflate(inflater)
        dialog.setContentView(binding.root)
        binding.btnKirim.setOnClickListener{
            val price = etHarga.editText?.text.toString()
            if (price == ""){
                Toast.makeText(requireContext(),"Masukkan Harga Tawar Anda", Toast.LENGTH_SHORT).show()
            }else{
                val harga = price.toInt()
                if (harga > 0){
                    dataHarga = PostBuyerOrderBody(harga,productId!!)
                    dataStore.getAccessToken().observe(viewLifecycleOwner){ access_token ->
                        viewModel.setBuyerOrder(dataHarga,access_token).observe(viewLifecycleOwner){
                            when(it.status){
                                Status.LOADING -> {
                                    showLoading(requireActivity())
                                }
                                Status.SUCCESS -> {
                                    showSnackbar(requireContext(),requireView(), "Harga Tawar Berhasil Dikirim", R.color.SUCCESS)
                                    btnTertarik.isVisible = false
                                    btnTertarikSuccess.isVisible = true
                                    hideLoading()
                                    dialog.dismiss()
                                }
                                Status.ERROR -> {
                                    showSnackbar(requireContext(), requireView(), it.message.toString(), R.color.DANGER)
                                    hideLoading()
                                    dialog.dismiss()
                                }
                            }
                        }

                    }
                } else {
                    Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return dialog
    }
}