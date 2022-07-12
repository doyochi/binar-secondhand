package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.data.remote.model.product.ProductSellerDtoItem
import id.hikmah.binar.secondhand.databinding.ProductListBinding

class ProductListAdapter: RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<ProductSellerDtoItem>() {
        override fun areItemsTheSame(
            oldItem: ProductSellerDtoItem,
            newItem: ProductSellerDtoItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductSellerDtoItem,
            newItem: ProductSellerDtoItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitListProduct(items: List<ProductSellerDtoItem>) = differ.submitList(items)

    inner class ProductListViewHolder(private val binding: ProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ProductSellerDtoItem){
            Glide.with(itemView.context)
                .load(items.imageUrl)
                .override(1000,1000)
                .into(binding.ivProduct)

            binding.tvProduct.text = items.name

            var category = ""

            if (items.categories != null) {
                for (i in items.categories.indices) {
                    category += if (i == items.categories.indices.last) {
                        items.categories[i].name
                    } else {
                        items.categories[i].name + ", "
                    }
                }
            } else {
                category = ""
            }

            binding.tvProductType.text = category

            binding.tvPrice.text = "Rp ${items.basePrice}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = ProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}