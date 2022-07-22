package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.data.remote.model.dto.Product
import id.hikmah.binar.secondhand.data.remote.model.dto.ProductItem
import id.hikmah.binar.secondhand.databinding.ItemDataBinding
import id.hikmah.binar.secondhand.helper.Converter

class ProductAdapter(private val onClickListers: (id: Int, product: ProductItem) -> Unit):
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    val diffCallback = object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private var differ = AsyncListDiffer(this, diffCallback)

    fun updateDataRecycler(product: Product?) = differ.submitList(product)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ProductViewHolder(private val binding: ItemDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductItem) {
            binding.idJudul.text = item.name
//            val kategoriProduct = item.categories.toString()
//            binding.idKategori.text = Category.kategoriProduct
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(binding.idImg)

            binding.idHarga.text = Converter.rupiah(item.basePrice)

            binding.itemData.setOnClickListener {
                onClickListers.invoke(item.id, item)
            }
        }
    }
}