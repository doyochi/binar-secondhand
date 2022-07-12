package id.hikmah.binar.secondhand.presentation.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.databinding.BottomSheetBinding


class BottomSheetFragment(private val onClicked : (price : Int) -> Unit) : BottomSheetDialogFragment(){

    private var _binding: BottomSheetBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransBottomSheetDialogStyle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val inflater = LayoutInflater.from(context)
        _binding = BottomSheetBinding.inflate(inflater)
        dialog.setContentView(binding.root)
        binding.btnKirim.setOnClickListener{
            val price = binding.etHarga.text.toString().toInt()
            dialog.dismiss()
            onClicked.invoke(price)
        }
        dialog.show()
        return dialog
    }
}