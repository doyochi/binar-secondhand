package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.databinding.AddButtonForProductSellerBinding
import id.hikmah.binar.secondhand.databinding.ProductListBinding
import id.hikmah.binar.secondhand.domain.ProductSeller

class ProductListAdapter: RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<ProductSeller>() {
        override fun areItemsTheSame(
            oldItem: ProductSeller,
            newItem: ProductSeller
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductSeller,
            newItem: ProductSeller
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitListProduct(items: List<ProductSeller>) = differ.submitList(items)

    inner class ProductListViewHolder(private val binding: ProductListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ProductSeller) {
            Glide.with(itemView.context)
                .load(items.productImage)
                .override(1000, 1000)
                .into(binding.ivProduct)

            binding.tvProduct.text = items.productName

            binding.tvProductType.text = items.productCategories

            binding.tvPrice.text = "Rp ${items.productPrice}"
        }
    }

    inner class AddButtonProductSeller(private val binding: AddButtonForProductSellerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = ProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }


    override fun getItemCount(): Int {
        return differ.currentList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position) + 1
    }
}