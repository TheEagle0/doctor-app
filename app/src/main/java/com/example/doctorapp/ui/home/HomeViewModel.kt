package com.example.doctorapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.firestore.Firestore
import com.example.doctorapp.models.RemoteResult
import com.example.doctorapp.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val liveUser = MutableLiveData<RemoteResult<out User>>()

    fun getUser(): MutableLiveData<RemoteResult<out User>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.getUser().collect {
                liveUser.postValue(it)
            }
        }
        return liveUser
    }
}