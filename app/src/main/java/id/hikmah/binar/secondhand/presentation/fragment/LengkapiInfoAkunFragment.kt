package id.hikmah.binar.secondhand.presentation.fragment

import android.app.Activity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.data.common.*
import id.hikmah.binar.secondhand.data.remote.service.ApiClient
import id.hikmah.binar.secondhand.data.remote.service.ApiService
import id.hikmah.binar.secondhand.data.repository.UserRepo
import id.hikmah.binar.secondhand.databinding.FragmentLengkapiInfoAkunBinding
import id.hikmah.binar.secondhand.helper.DatastoreManager
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.helper.viewModelsFactory
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_lengkapi_info_akun.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class LengkapiInfoAkunFragment : Fragment() {
    private var _binding: FragmentLengkapiInfoAkunBinding ?= null
    private val binding get() = _binding!!

    private var accessToken : String? = null

    private val fileUtil = FileUtil()
    private var uri : Uri? = null


    companion object { const val REQUEST_CODE_PERMISSION = 100 }

    private val api: ApiService by lazy { ApiClient.instance }
    private val userRepo: UserRepo by lazy { UserRepo(api) }
    private val viewModel: UserViewModel by viewModelsFactory { UserViewModel(userRepo) }

    private val pref: DatastoreManager by lazy { DatastoreManager(requireContext()) }
    private val dataStore: DatastoreViewModel by viewModelsFactory { DatastoreViewModel(pref) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLengkapiInfoAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        pickImgAndRequestPermission()
        saveProfile()
    }

    private fun saveProfile(){
        binding.apply {
            simpanProfileBtn.setOnClickListener{
                updateProfile()
            }
        }
    }

    private fun updateProfile(){
        val image = uri?.let { prepareFilePart(it) }

        var file = File(uri?.path)
        var reqFile : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)


        val etFullName = binding.namaEt.text.toString()
        val etCity = binding.kotaEt.text.toString()
        val etAddress = binding.alamatEt.text.toString()
        val etPhoneNumber = binding.nomorHpEt.text.toString()

        val fullName = etFullName.toRequestBody("full_name".toMediaTypeOrNull())
        val phoneNumber = etPhoneNumber.toRequestBody("phone_humber".toMediaTypeOrNull())
        val address = etAddress.toRequestBody("address".toMediaTypeOrNull())
        val city = etCity.toRequestBody("city".toMediaTypeOrNull())

        viewModel.editUser(
            accessToken!!,
            fullName,
            phoneNumber,
            address,
            city,
            image).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        hideLoading()
                        observeData()
                    }
                    Status.ERROR -> {
                        hideLoading()
                    }
                    Status.LOADING -> {
                        showLoading(requireActivity())
                    }
                }
        }
    }

    private fun prepareFilePart(fileUri: Uri): MultipartBody.Part {
        val file = File(fileUri.path!!)
        Log.i("PATH IMAGE", file.absolutePath)

        val requestFile: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

//        val requestFile: RequestBody = file.asRequestBody("image_url".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }

    private fun observeData() {
        dataStore.getAccessToken().observe(viewLifecycleOwner) { token ->
            accessToken = token
            viewModel.getUser(accessToken!!).observe(viewLifecycleOwner) {
                it.data?.let { data ->
                    binding.apply {

                        if (data.image != null) {
                            fotoAkun.setPadding(0,0,0,0)
                            Glide.with(requireContext())
                                .load(data.image)
                                .into(fotoAkun)
                        }

                        namaEt.setText(data.fullName)
                        kotaEt.setText(data.city)
                        alamatEt.setText(data.address)
                        nomorHpEt.setText(data.phoneNumber)
                    }
                }
            }
        }
    }

    private fun pickImgAndRequestPermission() {
        binding.fotoAkun.setOnClickListener {
            checkingPermission()
        }
    }

    private fun checkingPermission() {
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
            chooseImageDialog()
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
                showPermissionDeniedDialog()
            }

            else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }



    private fun chooseImageDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->

            binding.fotoAkun.apply {
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

                binding.fotoAkun.apply {
                    setPadding(0,0,0,0)
                    setImageBitmap(bitmap)
                }

                val imgPath = fileUtil.getPath(requireContext(), fileUtil.bitmapToUri(requireContext(), bitmap))

                uri = Uri.parse(imgPath)
            }
        }








}