package com.example.doctorapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    val adminEmail: String = "doctor@mail.com",
    val patientEmail: String = "",
    val messages: List<Message> = listOf()
) : Parcelable

@Parcelize
data class Message(val time: Long = 0, val message: String = "") : Parcelable
