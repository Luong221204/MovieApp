package com.example.movieapp.Application

import android.app.Application
import com.example.movieapp.LocalDatabase.LocalDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication:Application(){
    override fun onCreate() {
        super.onCreate()
    }
}