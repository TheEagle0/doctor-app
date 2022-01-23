/*
 * Copyright 2018 Fulltact
 * @author Muhammad Alkady @kady.muhammad@gmail.com
 */

package com.example.doctorapp.base

import androidx.multidex.MultiDexApplication

class DoctorApp : MultiDexApplication() {

    companion object {
        private lateinit var mutableAppInstance: DoctorApp
        val appInstance by lazy { mutableAppInstance }
    }

    override fun onCreate() {
        super.onCreate()
        mutableAppInstance = this
    }


}