package id.hikmah.binar.secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.secondhand.databinding.FragmentHomeBinding
import id.hikmah.binar.secondhand.databinding.FragmentSaleListBinding
import kotlinx.android.synthetic.main.layout_navbar.view.*

class SaleListFragment : Fragment() {

    private var _binding: FragmentSaleListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSaleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moveToJual()
        moveToNotif()
        moveToHome()
        moveToAkun()

    }

    private fun moveToHome() {
        binding.footer.footer_home.setOnClickListener{
            findNavController().navigate(R.id.action_saleListFragment_to_homeFragment)
        }
    }

    private fun moveToNotif() {
        binding.footer.footer_notif.setOnClickListener{
            findNavController().navigate(R.id.action_saleListFragment_to_notificationFragment)
        }
    }

    private fun moveToJual() {
        binding.footer.footer_jual.setOnClickListener{
            findNavController().navigate(R.id.action_saleListFragment_to_detailProdukFragment)
        }
    }

    private fun moveToAkun() {
        binding.footer.footer_akun.setOnClickListener{
            findNavController().navigate(R.id.action_saleListFragment_to_akunSayaFragment)
        }
    }


}