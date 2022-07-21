package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.data.remote.model.notification.NotificationDto
import id.hikmah.binar.secondhand.databinding.NotificationListBinding
import id.hikmah.binar.secondhand.helper.toDateFavorite

class NotificationAdapter(
    private val onClickCard: (id: Int) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<NotificationDto>() {
        override fun areItemsTheSame(oldItem: NotificationDto, newItem: NotificationDto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NotificationDto,
            newItem: NotificationDto
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitNotification(item: List<NotificationDto>) = differ.submitList(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            NotificationListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NotificationViewHolder(private val binding: NotificationListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NotificationDto) {
            binding.containerPenawaranP.setOnClickListener {
                onClickCard.invoke(item.id)
            }

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(binding.ivProduct)

            binding.tvProductTitle.text = item.productName
            binding.tvProductPrice.text = "Rp ${item.productNot.basePrice}"
            binding.tvProductInformation.text = "Ditawar Rp ${item.bidPrice}"

            if (!item.transactionDate.isNullOrEmpty()) {
                binding.tvDateProduct.text = item.transactionDate.toDateFavorite()
            }
        }
    }
}