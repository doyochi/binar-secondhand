package id.hikmah.binar.secondhand.presentation.fragment
//
//import android.app.Dialog
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.widget.Button
//import android.widget.Toast
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import com.google.android.material.textfield.TextInputLayout
//import id.hikmah.binar.secondhand.R
//import id.hikmah.binar.secondhand.data.common.showLoading
//import id.hikmah.binar.secondhand.data.remote.model.dto.buyer.PostBuyerOrderBody
//import id.hikmah.binar.secondhand.databinding.BottomSheetBinding
//import id.hikmah.binar.secondhand.helper.Status
//
//
//class BottomSheetFragment(private val onClicked : (price : Int) -> Unit) : BottomSheetDialogFragment(){
//
//    private var _binding: BottomSheetBinding? =null
//    private val binding get() = _binding!!
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
//        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
//        val inflater = LayoutInflater.from(context)
//        val etHarga = view.findViewById<TextInputLayout>(R.id.et_harga)
//        val btnTertarik = view.findViewById<Button>(R.id.btn_tertarik)
//        val btnTertarikSuccess = view.findViewById<Button>(R.id.btn_tertarik_success)
//        _binding = BottomSheetBinding.inflate(inflater)
//        dialog.setContentView(binding.root)
//        binding.btnKirim.setOnClickListener{
//            val price = etHarga.editText?.text.toString()
//            if (price == ""){
//                Toast.makeText(requireContext(),"Masukkan Harga Tawar Anda", Toast.LENGTH_SHORT).show()
//            }else{
//                val harga = price.toInt()
//                if (harga > 0){
//                    dataHarga = PostBuyerOrderBody(harga,productId!!)
//                    dataStore.getAccessToken().observe(viewLifecycleOwner){ access_token ->
//                        viewModel.setBuyerOrder(dataHarga,access_token).observe(viewLifecycleOwner){
//                            when(it.status){
//                                Status.LOADING -> {
//                                    showLoading(requireActivity())
//                                }
//                                Status.SUCCESS -> {
//                                    showSnackbar(requireContext(),requireView(), "Harga Tawar Berhasil Dikirim", R.color.SUCCESS)
//                                    btnTertarik.isVisible = false
//                                    btnTertarikSuccess.isVisible = true
//                                    hideLoading()
//                                    dialog.dismiss()
//                                }
//                                Status.ERROR -> {
//                                    showSnackbar(requireContext(), requireView(), it.message.toString(), R.color.DANGER)
//                                    hideLoading()
//                                    dialog.dismiss()
//                                }
//                            }
//                        }
//
//                    }
//                } else {
//                    Toast.makeText(requireContext(),"",Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//        return dialog
//    }
//}