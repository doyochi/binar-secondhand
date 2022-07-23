package id.hikmah.binar.secondhand.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.databinding.BottomSheetInfoPenawarBinding
import id.hikmah.binar.secondhand.databinding.FragmentInfoPenawarBinding
import id.hikmah.binar.secondhand.databinding.LayoutStatusBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.helper.toDateFavorite
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.InfoPenawarViewModel

@AndroidEntryPoint
class InfoPenawarFragment : Fragment() {
    private var _binding: FragmentInfoPenawarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InfoPenawarViewModel by viewModels()
    private val dataStore: DatastoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInfoPenawarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPenawarBack()

        val orderId = InfoPenawarFragmentArgs.fromBundle(arguments as Bundle).productId
        dataStore.getAccessToken().observe(viewLifecycleOwner) { token ->
            fetchSellerOrderById(token, orderId)
        }
    }

    private fun btnLogicClicked(token: String, id: Int, status: String) {
        when (status) {
            "accepted" -> {

                binding.btnPenawaranTolak.text = "Status"
                binding.btnPenawaranTerima.text = "Hubungi via WhatsApp"

                binding.btnPenawaranTolak.setOnClickListener {
                    statusButton(token, id)
                }

                binding.btnPenawaranTerima.setOnClickListener { hubungiButton(token, id) }

            }
            else -> {

                binding.btnPenawaranTolak.text = "Tolak"
                binding.btnPenawaranTerima.text = "Terima"

                binding.btnPenawaranTolak.setOnClickListener {
                    declinedOrder(token, id)
                }

                binding.btnPenawaranTerima.setOnClickListener {
                    acceptedOrder(token, id)
                }

            }
        }
    }

    private fun fetchSellerOrderById(token: String, orderId: Int) {
        viewModel.fetchOrderById(token, orderId).observe(viewLifecycleOwner) { res ->
            when (res.status) {

                Status.LOADING -> {}

                Status.ERROR -> {
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show()
                }

                Status.SUCCESS -> {
                    val data = res.data!!

                    binding.tvSellerName.text = data.user.fullName
                    binding.tvSellerCity.text = data.user.city

                    binding.tvProductName.text = data.productName
                    binding.tvProductBasePrice.text = "Rp ${data.basePrice}"
                    binding.tvProductBidPrice.text = "Ditawar Rp ${data.price}"

                    binding.tvTransactionDate.text = data.transactionDate.toDateFavorite()

                    btnLogicClicked(token, data.id, data.status)
                }

            }
        }
    }

    private fun acceptedOrder(token: String, id: Int) {
        viewModel.patchOrderById(token, id, "accepted").observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun declinedOrder(token: String, id: Int) {
        viewModel.patchOrderById(token, id, "declined").observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun hubungiButton(token: String, id: Int) {
        val dialog = BottomSheetDialog(requireContext())

        val dialogBinding =
            BottomSheetInfoPenawarBinding.inflate(LayoutInflater.from(context), null, false)

        dialog.setContentView(dialogBinding.root)

        dialog.show()

        viewModel.fetchOrderById(token, id).observe(viewLifecycleOwner) { res ->
            when (res.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    res.data?.let { data ->
                        Glide.with(requireContext()).load(data.imageProduct)
                            .into(dialogBinding.ivProductImage)
                        dialogBinding.tvProductName.text = data.productName
                        dialogBinding.tvProductBasePrice.text = "Rp ${data.basePrice}"
                        dialogBinding.tvProductBidPrice.text = "Ditawar Rp ${data.price}"
                        dialogBinding.tvBuyerName.text = data.user.fullName
                        dialogBinding.tvBuyerCity.text = data.user.city

                        dialogBinding.btnHubungiViaWhatsapp.setOnClickListener {
                            openWhatsapp("+62${data.user.phoneNumber}")
                        }
                    }
                }

                Status.ERROR -> {}
            }
        }
        dialog.show()
    }

    private fun statusButton(token: String, id: Int) {
        val dialog = BottomSheetDialog(requireContext())

        val dialogBinding = LayoutStatusBinding.inflate(LayoutInflater.from(context), null, false)

        dialog.setContentView(dialogBinding.root)
        dialog.show()

        var status: String? = null

        dialogBinding.radioSuccess.setOnClickListener {
            dialogBinding.btnSetStatus.isEnabled = true
            status = "accepted"
        }

        dialogBinding.radioCancel.setOnClickListener {
            dialogBinding.btnSetStatus.isEnabled = true
            status = "declined"
        }

        dialogBinding.btnSetStatus.setOnClickListener {
            if (!status.isNullOrEmpty()) {
                viewModel.patchOrderById(token, id, status!!).observe(viewLifecycleOwner) { res ->
                    when (res.status) {
                        Status.LOADING -> {}
                        Status.ERROR -> {
                            Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT).show()
                        }
                        Status.SUCCESS -> {
                            //showing custom snackbar
                        }
                    }
                }
            }
            dialog.dismiss()
        }


    }

    private fun openWhatsapp(smsNumber: String) {
        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello,thanks for ordering my product")
            sendIntent.putExtra("jid", "$smsNumber@s.whatsapp.net")
            sendIntent.setPackage("com.whatsapp")
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error\n$e", Toast.LENGTH_SHORT).show()
        }
    }


    private fun btnPenawarBack() {
        binding.btnPenawarBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}