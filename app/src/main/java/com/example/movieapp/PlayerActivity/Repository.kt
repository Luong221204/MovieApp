package com.example.movieapp.PlayerActivity

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.movieapp.DetailFimActivity.DetailMovieRepository
import com.example.movieapp.domain.FilmItemModel.FilmItemModel

class Repository(
    private val filmItemModel: FilmItemModel,
    private val context: Context
) {
    val player:Player =ExoPlayer.Builder(context).build()


}