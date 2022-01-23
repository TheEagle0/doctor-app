package com.example.doctorapp.ui.add_user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.firestore.Firestore
import com.example.doctorapp.main.MainViewModel
import com.example.doctorapp.models.Chat
import com.example.doctorapp.models.RemoteResult
import com.example.doctorapp.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class AddUserViewModel : MainViewModel() {


    private val liveAddUser = MutableLiveData<RemoteResult<out AuthResult?>>()
    private val liveAddUserDoc = MutableLiveData<RemoteResult<out DocumentReference>>()
    private val liveAddChatDoc = MutableLiveData<RemoteResult<out DocumentReference>>()

    fun addUser(
        email: String,
        password: String,
        phone: String
    ): MutableLiveData<RemoteResult<out AuthResult?>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.createUserWithEmailAndPassword(email, password, phone).collect {
                liveAddUser.postValue(it)
            }
        }
        return liveAddUser;
    }

    fun createUserDoc(user: User): MutableLiveData<RemoteResult<out DocumentReference>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.addUserDoc(user).collect {
                liveAddUserDoc.postValue(it)
            }
        }
        return liveAddUserDoc
    }

    fun createChatDoc(chat: Chat): MutableLiveData<RemoteResult<out DocumentReference>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.addChatDoc(chat).collect {
                liveAddChatDoc.postValue(it)
            }
        }
        return liveAddChatDoc
    }

}