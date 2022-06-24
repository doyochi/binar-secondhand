package id.hikmah.binar.secondhand

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.secondhand.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.layout_navbar.view.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moveToNotif()
        moveToJual()
        moveToDaftarJual()
        moveToAkun()
    }

    private fun moveToNotif() {
        binding.footer.footer_notif.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
        }
    }

    private fun moveToJual() {
        binding.footer.footer_jual.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_detailProdukFragment)
        }
    }

    private fun moveToDaftarJual() {
        binding.footer.footer_daftar_jual.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_saleListFragment)
        }
    }

    private fun moveToAkun() {
        binding.footer.footer_akun.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_akunSayaFragment)
        }
    }

}