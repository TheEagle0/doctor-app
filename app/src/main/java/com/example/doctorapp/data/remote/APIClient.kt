///*
// * Copyright 2018 Fulltact
// * @author Muhammad Alkady @kady.muhammad@gmail.com
// */
//
//package com.example.doctorapp.data.remote
//
//import android.app.Application
//import arrow.syntax.function.memoize
//import com.example.doctorapp.R
//import com.example.doctorapp.base.DoctorApp
//import com.fulltact.BuildConfig
//import com.fulltact.R
//import com.fulltact.base.FulltactApp
//import com.fulltact.extensions.isDebugging
//import com.fulltact.extensions.isNotConnectedToInternet
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.Response
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.net.SocketTimeoutException
//import java.net.UnknownHostException
//import java.util.concurrent.TimeUnit
//
//object APIClient {
//
//    private val app: DoctorApp = DoctorApp.appInstance
//
//
//
//    val fulltactMemoizedEndPoints: () -> EndPoints = { getFulltactEndPoints() }.memoize()
//
//
//
//    private fun endPointsFromBaseURI(baseURI: String): EndPoints {
//        val builder: Retrofit.Builder = createRetrofitBuilder(baseURI, getOkHttpClient())
//        addGsonConverter(builder)
//        addCoroutinesConverter(builder)
//        return builder.build().create(EndPoints::class.java)
//    }
//
//    private fun getFulltactEndPoints(): EndPoints {
//        return endPointsFromBaseURI(FULLTACT_BASE_URL)
//    }
//
//    private fun getSilverFulltactEndPoint(): EndPoints {
//        return endPointsFromBaseURI(SILVER_FULLTACT_ID_URL)
//    }
//
//    private fun getLinkedIdAPIEndPoints(): EndPoints {
//        return endPointsFromBaseURI(LINKED_IN_ACCOUNT_BASE_URL)
//    }
//
//    private fun getLinkedInEndPoint(): EndPoints {
//        return endPointsFromBaseURI(LINKED_IN_ACCESS_TOKEN_BASE_URL)
//    }
//
//    private fun getCitiesEndPoint(): EndPoints {
//        return endPointsFromBaseURI(CITIES_API_URL)
//    }
//
//    private fun getElasticSearchLogsEndPoint(): EndPoints {
//        return endPointsFromBaseURI(ELASTIC_SEARCH_LOGS_BASE_URL)
//    }
//
//    private fun getElasticSearchProfilesEndPoint(): EndPoints {
//        return endPointsFromBaseURI(ELASTIC_SEARCH_PROFILES_BASE_URL)
//    }
//
//    private fun addCoroutinesConverter(builder: Retrofit.Builder): Retrofit.Builder =
//            builder.addCallAdapterFactory(CoroutineCallAdapterFactory())
//
//    private fun addGsonConverter(builder: Retrofit.Builder): Retrofit.Builder =
//            builder.addConverterFactory(GsonConverterFactory.create(getGson()))
//
//    private fun createRetrofitBuilder(baseUrl: String, client: OkHttpClient): Retrofit.Builder =
//            Retrofit.Builder().baseUrl(baseUrl).client(client)
//
//    private fun getGson(): Gson = GsonBuilder().setLenient().create()
//
//    private fun getOkHttpClient(): OkHttpClient {
//        val clientBuilder = OkHttpClient.Builder()
//        addInterceptors(clientBuilder, ErrorHandlerInterceptor(app))
//        if (isDebugging()) addInterceptors(clientBuilder, getLoggingInterceptor())
//        setTimeout(clientBuilder)
//        return clientBuilder.build()
//    }
//
//    private fun getLoggingInterceptor(): Interceptor {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        return loggingInterceptor
//    }
//
//    private fun setTimeout(builder: OkHttpClient.Builder,
//                           timeout: Long = CONNECTION_TIME_OUT_IN_SEC) {
//        with(builder) {
//            connectTimeout(timeout, TimeUnit.SECONDS)
//            readTimeout(timeout, TimeUnit.SECONDS)
//            writeTimeout(timeout, TimeUnit.SECONDS)
//        }
//    }
//
//    private fun addInterceptors(builder: OkHttpClient.Builder, vararg interceptors: Interceptor) =
//            interceptors.forEach { builder.addInterceptor(it) }
//
//    private fun intercept(app: Application, chain: Interceptor.Chain): Response? {
//        return if (isNotConnectedToInternet()) throw NoConnectionException(app.getString(R.string.connection_error))
//        else {
//            val response: Response? = chain.proceed(chain.request())
//            if (response != null) {
//                val msg = response.message()
//                when (response.code()) {
//                    404 -> throw NotFoundException(app.getString(R.string.not_found_error))
//                    in 400..499 -> throw ClientException(msg)
//                    in 500..599 -> throw InternalServerException(msg)
//                }
//            }
//            response
//        }
//    }
//
//    private class ErrorHandlerInterceptor(val app: Application) : Interceptor {
//
//        override fun intercept(chain: Interceptor.Chain): Response? = intercept(app, chain)
//
//    }
//
//    fun getErrorFromException(t: Throwable, app: Application): Result.Error =
//            when (t) {
//                is ClientException -> Result.Error(t.msg)
//                is NotFoundException -> Result.Error(t.msg)
//                is NoConnectionException -> Result.Error(t.msg)
//                is InternalServerException -> Result.Error(t.msg)
//                is UnknownHostException -> Result.Error(app.getString(R.string.unknown_host_error))
//                is SocketTimeoutException -> Result.Error(app.getString(R.string.timeout_error))
//                else -> Result.Error(app.getString(R.string.unknown_error))
//            }
//}