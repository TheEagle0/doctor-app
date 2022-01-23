///*
// * Copyright 2018 Fulltact
// * @author Muhammad Alkady @kady.muhammad@gmail.com
// */
//
//package com.example.doctorapp.data.remote
//
//import arrow.core.Try
//import com.fulltact.data.remote.APIClient
//import com.fulltact.entities.*
//import com.fulltact.extensions.logD
//import com.github.scribejava.apis.TwitterApi
//import com.github.scribejava.core.builder.ServiceBuilder
//import com.github.scribejava.core.model.OAuth1RequestToken
//import com.github.scribejava.core.model.OAuthRequest
//import com.github.scribejava.core.model.Verb
//import kotlinx.coroutines.Deferred
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.sync.Mutex
//import kotlinx.coroutines.sync.withLock
//import kotlinx.coroutines.withContext
//import retrofit2.Response
//import kotlin.coroutines.CoroutineContext
//
//object APIManager {
//
//    private const val TAG = "APIManager"
//
//
//    private val mutex = Mutex()
//
//    private val fulltactEndPoints = suspend { mutex.withLock { APIClient.fulltactMemoizedEndPoints() } }
//
//
//    suspend fun sendRecentsToElasticSearchAsync(logs: String): Deferred<SendRecentsResponse> {
//        return elasticSearchLogsEndPoints()().sendRecentsToElasticSearchAsync(logs)
//    }
//
//}