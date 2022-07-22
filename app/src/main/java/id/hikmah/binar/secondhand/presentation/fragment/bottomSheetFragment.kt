package id.hikmah.binar.secondhand.presentation.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.hideLoading
import id.hikmah.binar.secondhand.data.common.showLoading
import id.hikmah.binar.secondhand.data.common.showSnackbar
import id.hikmah.binar.secondhand.data.common.toRp
import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.PostBuyerOrderBody
import id.hikmah.binar.secondhand.databinding.BottomSheetBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFragment(private val onClicked : (price : String) -> Unit) : BottomSheetDialogFragment(){

    private var _binding: BottomSheetBinding? =null
    private val binding get() = _binding!!

    private var productId : Int? = null
    private lateinit var dataHarga : PostBuyerOrderBody
    private val viewModel : Buyer6ViewModel by viewModels()
    private val dataStore : DatastoreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)

        productId = arguments?.getInt("product_key")!!
        productId?.let { getData(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getData(id: Int){
        viewModel.getProductDetail(id).observe(viewLifecycleOwner){
            it.data.let { data ->
                Glide.with(requireContext()).load(data?.imageUrl).into(binding.ivItemBid)
                binding.tvNameItemBid.text = data?.namaBarang
                binding.tvPriceItemBid.text = data?.hargaBarang?.toRp()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setContentView(view)
        dialog.show()

        val inflater = LayoutInflater.from(context)
        val etHarga = view.findViewById<TextInputLayout>(R.id.et_harga)
        val ivBarang = view.findViewById<ShapeableImageView>(R.id.iv_item_bid)
        val btnTertarik = view.findViewById<Button>(R.id.btn_tertarik)
        val btnTertarikSuccess = view.findViewById<Button>(R.id.btn_tertarik_success)
        val namaBarang = view.findViewById<TextView>(R.id.tv_name_item_bid)
        val hargaBarang = view.findViewById<TextView>(R.id.tv_price_item_bid)


        viewModel.getProductDetail(productId!!).observe(viewLifecycleOwner){
            it.data?.let { data ->
                Glide.with(requireContext()).load(data.imageUrl)
                    .into(ivBarang)
                namaBarang.text = data.namaBarang
                hargaBarang.text = data.hargaBarang.toRp()
            }
        }

        _binding = BottomSheetBinding.inflate(inflater)
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