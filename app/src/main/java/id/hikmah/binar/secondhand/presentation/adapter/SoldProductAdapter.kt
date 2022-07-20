package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.data.remote.model.notification.SoldProductDtoItem
import id.hikmah.binar.secondhand.databinding.SoldListBinding

class SoldProductAdapter: RecyclerView.Adapter<SoldProductAdapter.SoldProductViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<SoldProductDtoItem>() {
        override fun areItemsTheSame(
            oldItem: SoldProductDtoItem,
            newItem: SoldProductDtoItem
        ): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(
            oldItem: SoldProductDtoItem,
            newItem: SoldProductDtoItem
        ): Boolean {
            return newItem.hashCode() == oldItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(item: List<SoldProductDtoItem>) = differ.submitList(item)

    inner class SoldProductViewHolder(private val binding: SoldListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: SoldProductDtoItem) {
                Glide.with(itemView.context)
                    .load(items.imageUrl)
                    .into(binding.ivProduct)

                binding.tvProductTitle.text = items.productName

                binding.tvProductPrice.text = "Rp ${items.price}"

                binding.tvProductStatus.text = "Status - ${items.status}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoldProductViewHolder {
        val binding = SoldListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SoldProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoldProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}