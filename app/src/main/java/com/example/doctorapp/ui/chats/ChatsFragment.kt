package com.example.doctorapp.ui.chats

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doctorapp.R
import com.example.doctorapp.data.local.Prefs
import com.example.doctorapp.databinding.FragmentHomeBinding
import com.example.doctorapp.models.RemoteResult
import com.example.doctorapp.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.chats_fragment.*

class ChatsFragment : Fragment() {

    private val viewModel: ChatsViewModel by lazy { ViewModelProvider(this).get(ChatsViewModel::class.java) }
    private val adapter by lazy { ChatsAdapter(mutableListOf(), this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chats_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRV()
        if (Prefs.getString("email") == "doctor@mail.com")
            getAllChats()
        else getUserChat()
    }

    private fun getAllChats() {
        viewModel.getAllChats().observe(viewLifecycleOwner, {
            when (it) {
                is RemoteResult.Success -> {
                    it.data
                    adapter.updateAdapter(it.data)
                }
                is RemoteResult.Fail -> {
                    println(it.message)
                    Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getUserChat() {
        viewModel.getChats().observe(viewLifecycleOwner, {
            when (it) {
                is RemoteResult.Success -> adapter.updateAdapter(listOf(it.data))
                is RemoteResult.Fail -> Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
    })
}

private fun setUpRV() {
    chatsRV.layoutManager = LinearLayoutManager(requireContext())
    chatsRV.adapter = adapter
}
}