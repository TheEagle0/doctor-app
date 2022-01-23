package com.example.doctorapp.ui.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.models.Message
import com.example.doctorapp.models.RemoteResult
import com.example.doctorapp.ui.chats.ChatsAdapter
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.android.synthetic.main.chats_fragment.*

class ChatFragment : Fragment() {


    private val viewModel: ChatViewModel by lazy { ViewModelProvider(this).get(ChatViewModel::class.java) }
    private val email: String? by lazy { requireArguments().getString("mail") }
    private val adapter by lazy { MessagesAdapter(mutableListOf()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getChat()
        onSendClick()
        setUpRV()
    }

    private fun getChat() {
        viewModel.getChat(email!!).observe(viewLifecycleOwner, {
            when (it) {
                is RemoteResult.Success -> {
                    viewModel.chat = it.data
                    viewModel.messagesList.addAll(it.data.messages)
                    adapter.updateAdapter(it.data.messages)
                }
                is RemoteResult.Fail -> Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateChat(message: Message) {
        println("8888888" + viewModel.chat)
        viewModel.messagesList.add(message)
        viewModel.update(viewModel.chat?.copy(messages = viewModel.messagesList) ?: return)
            .observe(viewLifecycleOwner, {
                when (it) {
                    is RemoteResult.Success -> {messageET.setText("")
                    }
                    is RemoteResult.Fail -> {
                    }
                }
            })
    }

    private fun setUpRV() {
        messagesRV.layoutManager = LinearLayoutManager(requireContext())
        messagesRV.adapter = adapter
    }

    private fun onSendClick() {
        sendButton.setOnClickListener {
            val message = messageET.text.toString()
            if (message.isNotEmpty()) {
                updateChat(Message(message = message))
            }
        }
    }
}