package com.example.doctorapp.ui.chats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.firestore.Firestore
import com.example.doctorapp.models.Chat
import com.example.doctorapp.models.RemoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChatsViewModel : ViewModel() {
    private val liveChats = MutableLiveData<RemoteResult<out Chat>>()
    private val liveAllChats = MutableLiveData<RemoteResult<out MutableList<Chat>>>()


    fun getChats(email:String): MutableLiveData<RemoteResult<out Chat>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.getChat(email).collect {
                liveChats.postValue(it)
            }
        }
        return liveChats
    }
    fun getAllChats(): MutableLiveData<RemoteResult<out MutableList<Chat>>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.getAllProducts().collect {
                liveAllChats.postValue(it)

            }
        }
        return liveAllChats
    }
}