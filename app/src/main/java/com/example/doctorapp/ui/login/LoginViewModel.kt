package com.example.doctorapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doctorapp.data.local.Prefs
import com.example.doctorapp.firestore.Firestore
import com.example.doctorapp.main.MainViewModel
import com.example.doctorapp.models.RemoteResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class LoginViewModel : MainViewModel() {

    private val liveSignIn = MutableLiveData<RemoteResult<out AuthResult?>>()

    fun login(email: String, password: String): MutableLiveData<RemoteResult<out AuthResult?>> {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.signInWithEmailAndPassword(email, password).collect {
                liveSignIn.postValue(it)
            }
        }
        return liveSignIn;
    }

    fun saveEmail(email: String) {
        Prefs.saveString("email", email)
    }
}