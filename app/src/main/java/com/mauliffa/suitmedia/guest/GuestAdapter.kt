package com.mauliffa.suitmedia.guest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mauliffa.suitmedia.R
import com.mauliffa.suitmedia.databinding.ItemGuestBinding

class GuestAdapter(private val listGuest: List<GuestResponseItem>): RecyclerView.Adapter<GuestAdapter.GridViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemGuestBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listGuest[position])
    }

    override fun getItemCount(): Int = listGuest.size

    inner class GridViewHolder(private val binding: ItemGuestBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(guest: GuestResponseItem) {
            Glide.with(itemView.context)
                .load(R.drawable.ic_guest)
                .apply(RequestOptions().override(400, 200))
                .into(binding.ivGuestPhoto)
            binding.tvGuestName.text = guest.name
            binding.tvGuestBirthdate.text = guest.birthdate
            itemView.setOnClickListener {  onItemClickCallback?.onItemClicked(guest) }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GuestResponseItem)
    }
}