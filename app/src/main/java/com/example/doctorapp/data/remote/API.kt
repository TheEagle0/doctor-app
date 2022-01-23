///*
// * Copyright 2018 Fulltact
// * @author Muhammad Alkady @kady.muhammad@gmail.com
// */
//
//package com.example.doctorapp.data.remote
//
//import java.io.IOException
//
//const val CODE_SUCCESS = 0
//
//sealed class Result {
//    data class Success<D>(val data: D) : Result() {
//
//        fun <E> map(transformer: (D) -> E): Success<E> {
//            return Success(transformer(data))
//        }
//
//    }
//
//    data class Error(val msg: String) : Result()
//}
//
//class NoConnectionException(val msg: String) : IOException()
//class NotFoundException(val msg: String) : IOException()
//class InternalServerException(val msg: String) : IOException()
//class ClientException(val msg: String) : IOException()