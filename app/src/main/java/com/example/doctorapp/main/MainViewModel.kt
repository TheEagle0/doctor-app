package com.example.doctorapp.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class MainViewModel : ViewModel() {
    val liveIsLoading = MutableLiveData<Boolean>()

    fun setLoading(isLoading: Boolean) {
        liveIsLoading.postValue(isLoading)
    }
}