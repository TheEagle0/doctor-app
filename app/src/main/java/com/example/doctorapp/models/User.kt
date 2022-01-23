package com.example.doctorapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val name: String = "", val email: String = "", val phone: String = "") : Parcelable
