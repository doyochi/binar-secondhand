package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.databinding.FavoriteListBinding
import id.hikmah.binar.secondhand.domain.FavoriteProduct
import id.hikmah.binar.secondhand.helper.toDateFavorite

class FavoriteProductAdapter(private val onClickListener: (id: Int) -> Unit): RecyclerView.Adapter<FavoriteProductAdapter.FavoriteProductViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(
            oldItem: FavoriteProduct,
            newItem: FavoriteProduct
        ): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(
            oldItem: FavoriteProduct,
            newItem: FavoriteProduct
        ): Boolean {
            return newItem.hashCode() == oldItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitData(item: List<FavoriteProduct>) = differ.submitList(item)

    inner class FavoriteProductViewHolder(private val binding: FavoriteListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FavoriteProduct) {

            if (!items.productTransactionDate.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(items.productImage)
                    .into(binding.ivProduct)

                binding.tvProductTitle.text = items.productName

                binding.tvProductPrice.text = "Rp ${items.productPrice}"

                binding.tvProductInformation.text = "Ditawar Rp ${items.productBidPrice}"

                binding.tvDateProduct.text = items.productTransactionDate.toDateFavorite()

                binding.containerPenawaranP.setOnClickListener {
                    onClickListener.invoke(items.id)
                }
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