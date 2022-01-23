package com.example.doctorapp.models

sealed class RemoteResult<T> {
    object Loading : RemoteResult<Nothing>()
    data class Success<T>(val data: T) : RemoteResult<T>()
    data class Fail(val message: String) : RemoteResult<Nothing>()
    companion object {
        fun <T> success(data: T) = Success(data)
    }
}
