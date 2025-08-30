package com.example.movieapp.PlayerActivity

import android.content.Context
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.UnstableApi
import com.example.movieapp.domain.FilmItemModel.FilmItemModel

class PlayMovieFactory constructor(
    val context: Context,
    val filmItemModel: FilmItemModel
):ViewModelProvider.Factory {
    @OptIn(UnstableApi::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PlayVideoViewmodel::class.java)){
            return PlayVideoViewmodel(context,filmItemModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}