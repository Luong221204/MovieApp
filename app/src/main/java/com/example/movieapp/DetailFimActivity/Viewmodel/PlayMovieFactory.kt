package com.example.movieapp.DetailFimActivity.Viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayMovieFactory constructor(
    val context: Context
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayVideoViewmodel::class.java)){
            return PlayVideoViewmodel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}