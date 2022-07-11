package id.hikmah.binar.secondhand

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import id.hikmah.binar.secondhand.databinding.BottomSheetBinding
import kotlinx.android.synthetic.main.bottom_sheet.*


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