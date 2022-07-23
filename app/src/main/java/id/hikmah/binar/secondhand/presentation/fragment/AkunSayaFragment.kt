package id.hikmah.binar.secondhand.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.databinding.FragmentAkunSayaBinding
import kotlinx.android.synthetic.main.layout_navbar.view.*


class AkunSayaFragment : Fragment() {
    private var _binding : FragmentAkunSayaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAkunSayaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToHome()
        moveNotif()
        moveToJual()
        moveToDaftarJual()
        moveToLengkapiInfoAkun()
        toLogin()
    }

    private fun toLogin(){
        binding.ivLogin.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_loginFragment)
        }
    }

    private fun moveToHome() {
        binding.footer.footer_home.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_homeFragment)
        }
    }

    private fun moveNotif() {
        binding.footer.footer_notif.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_notificationFragment)
        }
    }

    private fun moveToJual() {
        binding.footer.footer_jual.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_detailProdukFragment)
        }
    }

    private fun moveToDaftarJual() {
        binding.footer.footer_daftar_jual.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_saleListFragment)
        }
    }

    private fun moveToLengkapiInfoAkun() {
        binding.tvEditAcc.setOnClickListener{
            findNavController().navigate(R.id.action_akunSayaFragment_to_lengkapiInfoAkunFragment)
        }
    }

}