package com.example.doctorapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.R
import com.example.doctorapp.models.Message
import kotlinx.android.synthetic.main.message_item.view.*


class MessagesAdapter(private val messages: MutableList<Message>) :
    RecyclerView.Adapter<MessagesAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val message = messages[position]
        with(holder.itemView) {
            name.text = message.sender
            messageTV.text = message.message
        }
    }

    fun updateAdapter(newMessages: List<Message>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    override fun getItemCount() = messages.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)


}

