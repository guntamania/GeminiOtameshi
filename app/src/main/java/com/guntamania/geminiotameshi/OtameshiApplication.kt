package com.guntamania.geminiotameshi

import android.app.Application
import android.content.Context

class OtameshiApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: OtameshiApplication

        fun getApplicationContext(): Context {
            return instance.applicationContext
        }
    }
}