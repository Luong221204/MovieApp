package com.example.movieapp.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.Player

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