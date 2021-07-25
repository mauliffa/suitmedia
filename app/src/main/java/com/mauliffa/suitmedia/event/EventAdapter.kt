package com.mauliffa.suitmedia.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mauliffa.suitmedia.databinding.ItemEventBinding

class EventAdapter(private val listEvent: ArrayList<Event>): RecyclerView.Adapter<EventAdapter.ListViewHolder>(){
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listEvent[position])
    }

    override fun getItemCount(): Int = listEvent.size

    inner class ListViewHolder(private val binding: ItemEventBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            Glide.with(itemView.context)
                .load(event.eventPhoto)
                .apply(RequestOptions().override(400, 200))
                .into(binding.ivEventPhoto)
            binding.tvEventName.text = event.eventName
            binding.tvEventDate.text = event.eventDate
            binding.btnChooseEvent.setOnClickListener {  onItemClickCallback?.onItemClicked(event)  }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Event)
    }
}