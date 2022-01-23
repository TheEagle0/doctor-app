package com.example.doctorapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.doctorapp.data.local.Prefs
import com.example.doctorapp.firestore.Firestore

open class MainViewModel : ViewModel() {
    val liveIsLoading = MutableLiveData<Boolean>()

    fun setLoading(isLoading: Boolean) {
        liveIsLoading.postValue(isLoading)
    }

    fun logout() {
        Firestore.logout()
        Prefs.saveString("email", "")
    }
}