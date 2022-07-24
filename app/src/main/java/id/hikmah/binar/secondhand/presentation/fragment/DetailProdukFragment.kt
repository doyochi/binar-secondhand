package id.hikmah.binar.secondhand.presentation.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.secondhand.helper.FileUtil
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.hideLoading
import id.hikmah.binar.secondhand.data.common.showLoading
import id.hikmah.binar.secondhand.data.common.showSnackbar
import id.hikmah.binar.secondhand.data.local.DatabaseSecondHand
import id.hikmah.binar.secondhand.data.local.entity.PreviewSellerProduct
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.repository.ProductRepo
import id.hikmah.binar.secondhand.databinding.FragmentDetailProdukBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.helper.mapper.SellerProductMapper
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.ProductSellerDetailsViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.ProductViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


@AndroidEntryPoint
class DetailProdukFragment : Fragment() {

    private var _binding: FragmentDetailProdukBinding? = null
    private val binding get() = _binding!!

    private val fileUtil = FileUtil()
    private var uri: Uri? = null
    companion object { const val REQUEST_CODE_PERMISSION = 100 }

    private var accessToken : String? = null
    private var imgSellerUrl : String? = null
    private var sellerName : String? = null
    private var city : String? = null

    private var namaCategory: String? = null
    private var idCategory: Int? = null

    private val viewModel: ProductSellerDetailsViewModel by viewModels()
    private val productRepo: ProductRepo by lazy {ProductRepo(ApiClient.instance)}
    private val productViewModel: ProductViewModel by lazy {ProductViewModel(productRepo)}
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
        spinnerCategory()
        reqPermission()
        terbitkanBarang()
        toPreview()

//        val productId = DetailProdukFragmentArgs.fromBundle(arguments as Bundle).productId
//
//        if (productId != 0) {
//            fetchProductSellerFromDaftarJual(productId)
//        }

        //fetch user
        accessToken = dataStore.getAccessToken().toString()

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

    private fun reqPermission(){
        binding.foto.setOnClickListener{
            if (isGranted(
                    requireActivity(),
                    Manifest.permission.CAMERA,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    REQUEST_CODE_PERMISSION,
                )
            ) {
                AlertDialog.Builder(requireContext())
                    .setMessage("Pilih Gambar")
                    .setPositiveButton("Gallery") { _, _ ->
                        requireActivity().intent.type = "image/*"
                        galleryResult.launch("image/*")
                    }
                    .setNegativeButton("Camera") { _, _ -> openCamera() }
                    .show()
            }
        }
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.foto.apply {
                setPadding(0,0,0,0)
                setImageURI(result)
            }
            val imgPath = result?.let { fileUtil.getPath(requireContext(), it) }
            uri = Uri.parse(imgPath)
        }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data!!.extras?.get("data") as Bitmap
                binding.foto.apply {
                    setPadding(0,0,0,0)
                    setImageBitmap(bitmap)
                }
                val imgPath = fileUtil.getPath(requireContext(), fileUtil.bitmapToUri(requireContext(), bitmap))
                uri = Uri.parse(imgPath)
            }
        }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun terbitkanBarang(){
        val image = uri?.let { orgFilePart(it) }
        val namaBarang = binding.namaProdukEt.toString().toRequestBody("name".toMediaTypeOrNull())
        val hargaBarang = binding.hargaProdukEt.toString().toRequestBody("baseprice".toMediaTypeOrNull())
        val kategoriBarang = binding.kategoriSpinner.selectedItem.toString().toRequestBody("categoriIds".toMediaTypeOrNull())
        val getCategory = binding.kategoriSpinner.selectedItem
        val idCategory = arrayOf(R.array.category_array).indexOf(getCategory)
        val descBarang = binding.deskripsiEt.toString().toRequestBody("description".toMediaTypeOrNull())
        val location = "Malang".toRequestBody()

        binding.btnTerbitkan.setOnClickListener{
            productViewModel.postProduct(accessToken!!,namaBarang,hargaBarang,idCategory,descBarang,location,image).observe(viewLifecycleOwner){
                when (it.status){
                    Status.SUCCESS -> {
                        hideLoading()
                        dataStore.saveMsgSnackbar("Produk berhasil diterbitkan")
                        findNavController().navigate(R.id.action_detailProdukFragment_to_saleListFragment)
                    }
                    Status.ERROR -> {
                        hideLoading()
                        showSnackbar(requireContext(), requireView(), it.message!!, R.color.DANGER)
                    }
                    Status.LOADING -> {
                        showLoading(requireActivity())
                    }
                }

            }
        }
    }

    private fun orgFilePart(fileUri: Uri): MultipartBody.Part {
        val file = File(fileUri.path)
        Log.i("PATH IMAGE", file.absolutePath)
        val requestFile: RequestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    private fun toPreview() {
        binding.btnPreview.setOnClickListener{
            val imgUri = uri!!
            val namaBarang = binding.namaProdukEt.toString()
            val hargaBarang = binding.hargaProdukEt.toString()
            val kategoriBarang = binding.kategoriSpinner.selectedItem.toString()
            val descBarang = binding.deskripsiEt.toString()

            val bundleProduct = PreviewSellerProduct(accessToken,namaBarang,idCategory, imgUri, kategoriBarang, hargaBarang, descBarang, imgSellerUrl, sellerName, city)

            val bundle = Bundle()
            bundle.putParcelable("key_preview", bundleProduct)
            setFragmentResult("previewreq", bundle)

            findNavController().navigate(R.id.action_detailProdukFragment_to_previewProdukFragment)

        }
    }

    private fun fetchUsers(){
        dataStore.getAccessToken().observe(viewLifecycleOwner) { result ->
            accessToken = result
//            when (result.status) {
//                Status.LOADING -> {}
//                Status.SUCCESS -> {
////                    binding.tvSellerName.text = result.data?.fullName
////                    binding.tvSellerCity.text = result.data?.city
//                }
//                Status.ERROR -> {}
//            }
        }

    }

    //category
    private fun spinnerCategory(){
        val spinner: Spinner = binding.kategoriSpinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                val getCategory = spinner.selectedItem
                namaCategory = spinner.selectedItem.toString()
                idCategory = arrayOf(R.array.category_array).indexOf(getCategory)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


    }

}