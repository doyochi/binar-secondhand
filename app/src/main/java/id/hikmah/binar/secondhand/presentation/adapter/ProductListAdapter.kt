package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.R
import id.hikmah.binar.secondhand.databinding.AddButtonForProductSellerBinding
import id.hikmah.binar.secondhand.databinding.ProductListBinding
import id.hikmah.binar.secondhand.domain.ProductSeller

class ProductListAdapter(
    private val onClickButton: () -> Unit,
    private val onClickItem: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            R.layout.product_list -> {
                val binding =
                    ProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductListViewHolder(binding)
            }

            R.layout.add_button_for_product_seller -> {
                val binding = AddButtonForProductSellerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AddButtonProductSeller(binding)
            }

            else -> {
                throw IllegalArgumentException("unknown view type $viewType")
            }

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                R.layout.add_button_for_product_seller
            }
            else -> {
                R.layout.product_list
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.product_list -> (holder as ProductListViewHolder).bindProductList(differ.currentList[position - 1])
            R.layout.add_button_for_product_seller -> (holder as AddButtonProductSeller).bindAddButton()
        }
    }

    inner class ProductListViewHolder(private val binding: ProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindProductList(items: ProductSeller) {

            binding.cvProductList.setOnClickListener {
                onClickItem.invoke(items.productId)
            }

            Glide.with(itemView.context)
                .load(items.productImage)
                .override(1000, 1000)
                .into(binding.ivProductList)

            binding.tvProductList.text = items.productName

            binding.tvProductTypeList.text = items.productCategories

            binding.tvPriceList.text = "Rp ${items.productPrice}"
        }
    }

    inner class AddButtonProductSeller(private val binding: AddButtonForProductSellerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindAddButton() {
            binding.btnAddProductList.setOnClickListener {
                onClickButton.invoke()
            }
        }
    }
}