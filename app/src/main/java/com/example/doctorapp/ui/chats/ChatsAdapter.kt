package com.example.doctorapp.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.doctorapp.R
import com.example.doctorapp.models.Chat
import kotlinx.android.synthetic.main.chat_item.view.*


class ChatsAdapter(private val chats: MutableList<Chat>, val fragment: ChatsFragment) :
    RecyclerView.Adapter<ChatsAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val chat = chats[position]
        with(holder.itemView) {
            name.text = chat.patientEmail
            this.setOnClickListener {
//                fragment.findNavController().navigate(R.id.editProductFragment, Bundle().apply {
//                    putString("name", product.name)
//                    putString("company", product.company)
//                    putString("id", product.id)
//                })
            }

        }
    }

    fun updateAdapter(newChats: List<Chat>) {
        chats.clear()
        chats.addAll(newChats)
        notifyDataSetChanged()
    }

    override fun getItemCount() = chats.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

