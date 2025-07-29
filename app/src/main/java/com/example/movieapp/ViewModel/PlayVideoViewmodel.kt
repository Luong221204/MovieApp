package com.example.movieapp.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class PlayVideoViewmodel @SuppressLint("StaticFieldLeak")
constructor(
    val context:Context
):ViewModel() {
    private var player:Player = ExoPlayer.Builder(context).build().apply {
        playWhenReady = true
   }
    fun playVideo():Player{
        return player.apply {
            setMediaItem(
                MediaItem.fromUri("https://res.cloudinary.com/dvtbqqggo/video/upload/v1753693231/gufohbwzevdtqgxfttux.mp4")
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}