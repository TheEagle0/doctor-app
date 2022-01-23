package com.example.doctorapp.firestore

import android.provider.Settings
import com.example.doctorapp.models.RemoteResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

object Firestore {
    private val auth by lazy { FirebaseAuth.getInstance() }

    /**
     * Firebase Firestore settings.
     *Enable caching.
     * */
    private val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings
        .Builder()
        .setPersistenceEnabled(true)
        .build()

    /**
     * */
    private val db: FirebaseFirestore by lazy {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.firestoreSettings = settings
        db
    }

    fun signInWithEmailAndPassword(email: String, password: String) = callbackFlow {
        send(RemoteResult.Loading)
        RemoteResult.success(
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    GlobalScope.launch(Dispatchers.IO) {
                        val result: AuthResult? = it.result
                        send(RemoteResult.success(result))

                    }
                }
            }
        )
        awaitClose { this.cancel() }
    }.flowOn(Dispatchers.IO)

}