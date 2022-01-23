package com.example.doctorapp.firestore

import com.example.doctorapp.extenssions.await
import com.example.doctorapp.models.Chat
import com.example.doctorapp.models.RemoteResult
import com.example.doctorapp.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
object Firestore {
    private val auth by lazy { FirebaseAuth.getInstance() }

    private const val USERS_COLLECTION = "users"
    private const val CHAT_COLLECTION = "chat"

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


    fun createUserWithEmailAndPassword(email: String, password: String, phone: String) =
        callbackFlow {
            send(RemoteResult.Loading)
            RemoteResult.success(
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
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

    fun addUserDoc(user: User): Flow<RemoteResult<out DocumentReference>> = flow {
        emit(RemoteResult.Loading)
        val productDocumentReference: DocumentReference? =
            db.collection(USERS_COLLECTION).add(user).await()
        emit(RemoteResult.success(productDocumentReference!!))
    }.catch {
        emit(RemoteResult.Fail(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun addChatDoc(chat: Chat): Flow<RemoteResult<out DocumentReference>> = flow {
        emit(RemoteResult.Loading)
        val productDocumentReference: DocumentReference? =
            db.collection(CHAT_COLLECTION).add(chat).await()
        emit(RemoteResult.success(productDocumentReference!!))
    }.catch {
        emit(RemoteResult.Fail(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}