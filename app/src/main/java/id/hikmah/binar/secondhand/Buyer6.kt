package id.hikmah.binar.secondhand

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import id.hikmah.binar.secondhand.databinding.FragmentBuyer6Binding
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_buyer6.*
import me.relex.circleindicator.CircleIndicator3

class Buyer6 : Fragment() {
    private var _binding: FragmentBuyer6Binding? = null
    private val binding get() = _binding!!

    private var imagesList = mutableListOf<Int>()
    lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBuyer6Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postToList()

        textView = binding.deskripsi.findViewById(R.id.deskripsi)
        val text: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        textView.text = text
        textView.movementMethod = ScrollingMovementMethod()

        view_pager2.adapter = ViewPagerAdapter(imagesList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = binding.indicator.findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(view_pager2)

        val bottomSheetFragment = BottomSheetFragment()
        binding.btnTertarik.setOnClickListener {
            bottomSheetFragment.show(parentFragmentManager, "BottomSheetDialog")
        }

    }


    private fun addToList(image: Int){
        imagesList.add(image)
    }

    private fun postToList(){
        for (i in 1..5){
            addToList(R.drawable.background_jam)
        }
    }

}