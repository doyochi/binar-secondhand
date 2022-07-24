package id.hikmah.binar.secondhand.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.hideLoading
import id.hikmah.binar.secondhand.data.common.showLoading
import id.hikmah.binar.secondhand.data.common.showSnackbar
import id.hikmah.binar.secondhand.data.common.toRp
import id.hikmah.binar.secondhand.data.local.entity.PreviewSellerProduct
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.repository.ProductRepo
import id.hikmah.binar.secondhand.databinding.FragmentPreviewProdukBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.ProductSellerDetailsViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.ProductViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PreviewProdukFragment : Fragment() {

    private var _binding: FragmentPreviewProdukBinding? = null
    private val binding get() = _binding!!

    private var accessToken : String? = null
    private var productName: String? = null
    private var imgUri: Uri? = null
    private var basePrice: String? = null
    private var productDescription: String? = null
    private var imgSellerUrl : String? = null
    private var sellerName : String? = null
    private var sellerLocation : String? = null

    private var namaCategory: String? = null
    private var idCategory: Int? = null

    private val productRepo: ProductRepo by lazy { ProductRepo(ApiClient.instance) }
    private val productViewModel: ProductViewModel by lazy { ProductViewModel(productRepo) }
    private val dataStore: DatastoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPreviewProdukBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(requestKey = "previewreq") { _, bundle ->
            val getData = bundle.getParcelable<PreviewSellerProduct>("key_preview") as PreviewSellerProduct

            accessToken = getData.token
            imgUri = getData.imageUri
            productName = getData.productName
            idCategory = getData.categoryId
            namaCategory = getData.categoryName
            basePrice = getData.basePrice
            productDescription = getData.productDescription
            imgSellerUrl = getData.sellerImgUrl
            sellerName = getData.sellerName
            sellerLocation = getData.sellerLocation


            binding.apply {
                binding.ivProduk.setImageURI(imgUri)
                tvNamaBarang.text = productName
                tvKategori.text = namaCategory
                tvHargaBarang.text = basePrice?.toInt()?.toRp()
                tvDeskripsi.text = productDescription
                tvLokasi.text = sellerLocation
                tvNamaPenjual.text = sellerName
                if (imgSellerUrl != null) {
                    Glide.with(requireContext())
                        .load(imgSellerUrl)
                        .into(ivPenjual)
                }
            }
        }
        setPreview()
    }

    private fun setPreview() {
        val previewSellerProduct = PreviewSellerProduct(accessToken!!,productName,idCategory,imgUri,namaCategory,basePrice,productDescription, imgSellerUrl,sellerName,sellerLocation)
        val bundle = Bundle()
        bundle.putParcelable("detailkey", previewSellerProduct)
        setFragmentResult("detailrequestkey", bundle)
    }

    private fun terbitkanBarang(){
        val image = imgUri?.let { orgFilePart(it) }
        val namaBarang = binding.tvNamaBarang.toString().toRequestBody("name".toMediaTypeOrNull())
        val hargaBarang = binding.tvHargaBarang.toString().toRequestBody("baseprice".toMediaTypeOrNull())
        val idCategory = binding.tvKategori.toString().toRequestBody("categoriIds".toMediaTypeOrNull())
        val descBarang = binding.tvDeskripsi.toString().toRequestBody("description".toMediaTypeOrNull())
        val location = "Malang".toRequestBody()

        val kategorin = arrayOf(R.array.category_array)
//        val idygy =
        binding.btnTerbitkan.setOnClickListener{
//            productViewModel.postProduct(accessToken!!,namaBarang,hargaBarang,namaCategory,descBarang,location,image).observe(viewLifecycleOwner){
//                when (it.status){
//                    Status.SUCCESS -> {
//                        hideLoading()
//                        dataStore.saveMsgSnackbar("Produk berhasil diterbitkan")
//                        findNavController().navigate(R.id.action_detailProdukFragment_to_saleListFragment)
//                    }
//                    Status.ERROR -> {
//                        hideLoading()
//                        showSnackbar(requireContext(), requireView(), it.message!!, R.color.DANGER)
//                    }
//                    Status.LOADING -> {
//                        showLoading(requireActivity())
//                    }
//                }
//
//            }
        }
    }

    private fun orgFilePart(fileUri: Uri): MultipartBody.Part {
        val file = File(fileUri.path)
        Log.i("PATH IMAGE", file.absolutePath)
        val requestFile: RequestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

}