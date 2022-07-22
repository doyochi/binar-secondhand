package id.hikmah.binar.secondhand.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.hikmah.binar.secondhand.databinding.NotificationListBinding
import id.hikmah.binar.secondhand.domain.Notification
import id.hikmah.binar.secondhand.helper.toDateFavorite

class NotificationAdapter(
    private val onClickCard: (id: Int) -> Unit,
    private val onClickNotification: (notificationId: Int) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Notification,
            newItem: Notification
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitNotification(item: List<Notification>) = differ.submitList(item)

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
        fun bind(item: Notification) {
            binding.containerPenawaranP.setOnClickListener {
                onClickCard.invoke(item.orderId)
                onClickNotification.invoke(item.notifId)
            }

            Glide.with(itemView.context)
                .load(item.productImage)
                .into(binding.ivProduct)

            if (item.status == "bid") {
                binding.tvProductInformation.text = "Ditawar Rp ${item.productBidPrice}"
            } else {
                binding.tvProductInformation.text = ""
            }

            binding.tvProductTitle.text = item.productName

            binding.tvProductPrice.text = "Rp ${item.productBasePrice}"



            if (item.transactionDate != null) {
                binding.tvDateProduct.text = item.transactionDate.toDateFavorite()
            }
        }
    }
}