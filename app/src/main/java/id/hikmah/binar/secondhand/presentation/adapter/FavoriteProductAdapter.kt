package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.data.remote.model.notification.FavoriteProductDtoItem
import id.hikmah.binar.secondhand.databinding.FavoriteListBinding
import id.hikmah.binar.secondhand.helper.toDateFavorite

class FavoriteProductAdapter: RecyclerView.Adapter<FavoriteProductAdapter.FavoriteProductViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<FavoriteProductDtoItem>() {
        override fun areItemsTheSame(
            oldItem: FavoriteProductDtoItem,
            newItem: FavoriteProductDtoItem
        ): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(
            oldItem: FavoriteProductDtoItem,
            newItem: FavoriteProductDtoItem
        ): Boolean {
            return newItem.hashCode() == oldItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(item: List<FavoriteProductDtoItem>) = differ.submitList(item)

    inner class FavoriteProductViewHolder(private val binding: FavoriteListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FavoriteProductDtoItem) {
            Glide.with(itemView.context)
                .load(items.imageUrl)
                .into(binding.ivProduct)

            binding.tvProductTitle.text = items.productName

            binding.tvProductPrice.text = "Rp ${items.productFav.basePrice}"

            binding.tvProductInformation.text = "Ditawar Rp ${items.bidPrice}"

            if (items.transactionDate != null) {
                binding.tvDateProduct.text = items.transactionDate.toDateFavorite().toString()
            } else {
                binding.tvDateProduct.text = ""
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        val binding = FavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}