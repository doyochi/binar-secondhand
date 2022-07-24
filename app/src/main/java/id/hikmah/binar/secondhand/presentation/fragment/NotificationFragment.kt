package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.databinding.FragmentNotificationBinding
import id.hikmah.binar.secondhand.helper.Status
import id.hikmah.binar.secondhand.presentation.adapter.NotificationAdapter
import id.hikmah.binar.secondhand.presentation.viewmodel.DatastoreViewModel
import id.hikmah.binar.secondhand.presentation.viewmodel.NotificationViewModel
import kotlinx.android.synthetic.main.layout_navbar.view.*

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationViewModel by viewModels()
    private val dataStore: DatastoreViewModel by viewModels()

    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavbar()

        dataStore.getAccessToken().observe(viewLifecycleOwner) { token ->
            fetchNotification(token)
            initRecyclerView(accessToken = token)
        }


    }

    private fun initRecyclerView(accessToken: String) {
        binding.rvNotification.apply {
            notificationAdapter = NotificationAdapter(
                onClickCard = { id ->
                    findNavController().navigate(
                        NotificationFragmentDirections.actionNotificationFragmentToInfoPenawarFragment(
                            id
                        )
                    )
                },
                onClickNotification = { id ->
                    viewModel.patchNotification(accessToken, id)
                        .observe(viewLifecycleOwner) { result ->
                            when (result.status) {
                                Status.ERROR -> {
                                    Toast.makeText(
                                        requireContext(),
                                        result.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                else -> {}
                            }
                        }
                }
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
        }
    }

    //logic
    private fun fetchNotification(accessToken: String) {
        viewModel.fetchNotification(accessToken).observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    notificationAdapter.submitNotification(result.data!!)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    //end

    //footer
    private fun bottomNavbar() {
        fun moveToHome() {
            binding.footer.footer_home.setOnClickListener {
                findNavController().navigate(R.id.action_notificationFragment_to_homeFragment)
            }
        }

        fun moveToJual() {
            binding.footer.footer_jual.setOnClickListener {
                findNavController().navigate(R.id.action_notificationFragment_to_detailProdukFragment)
            }
        }

        fun moveToDaftarJual() {
            binding.footer.footer_daftar_jual.setOnClickListener {
                findNavController().navigate(R.id.action_notificationFragment_to_saleListFragment)
            }
        }

        fun moveToAkun() {
            binding.footer.footer_akun.setOnClickListener {
                findNavController().navigate(R.id.action_notificationFragment_to_akunSayaFragment)
            }
        }

        moveToHome()
        moveToAkun()
        moveToJual()
        moveToDaftarJual()
    }


}