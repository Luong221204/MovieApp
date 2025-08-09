package com.example.movieapp.DetailFimActivity.Viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                MediaItem.fromUri("https://res.cloudinary.com/dvtbqqggo/video/upload/v1754030306/qma8aiuo0ebpf6miog0s.mp4")
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}