package com.example.doctorapp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.firestore.Firestore
import com.example.doctorapp.models.Chat
import com.example.doctorapp.models.Message
import com.example.doctorapp.models.RemoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val liveChat = MutableLiveData<RemoteResult<out Chat>>()
    private val liveUpdate=MutableLiveData<RemoteResult<out Unit>>()
    val  messagesList:MutableList<Message> = mutableListOf()
    var chat:Chat?=null
    fun getChat(email:String): MutableLiveData<RemoteResult<out Chat>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.getChat(email).collect {
                liveChat.postValue(it)
            }
        }
        return liveChat
    }

    fun update(chat: Chat): MutableLiveData<RemoteResult<out Unit>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.updatemessages(chat).collect {
                liveUpdate.postValue(it)
            }
        }
        return liveUpdate
    }
}