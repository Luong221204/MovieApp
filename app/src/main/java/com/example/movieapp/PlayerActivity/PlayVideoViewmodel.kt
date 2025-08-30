package com.example.movieapp.PlayerActivity

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.movieapp.DetailFimActivity.DetailMovieRepository
import com.example.movieapp.R
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UnstableApi
class PlayVideoViewmodel
constructor(
    private val repository:  Repository,
    val filmItemModel: FilmItemModel
):ViewModel() {
    var job : Job? = null

    private val detailMovieRepository = DetailMovieRepository(filmItemModel)
    val searchFilms = detailMovieRepository.searchedFilms

    val listCasts = detailMovieRepository.casts


    var isPlaying by mutableStateOf(false)

    var isFullScreen by mutableStateOf(false)

    var currentPosition by  mutableLongStateOf(0L)
    var current by mutableLongStateOf(0L)
    var duration by  mutableLongStateOf(filmItemModel.Trailer.Time.toLong()*1000)
    var isSeeking by  mutableStateOf(false)

    var isReady by mutableStateOf(false)
    var showControls by  mutableStateOf(false)


    var isBuffering by mutableStateOf(true)


    var player:Player = repository.player.apply {
        playWhenReady = true
        val mediaItem = MediaItem.Builder()
            .setUri(filmItemModel.Link)
            .setMimeType(MimeTypes.VIDEO_MP4)
            .build()
        setMediaItem(mediaItem)
        prepare()
        addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    showControls=true
                    job?.cancel()
                }else if(state == Player.STATE_READY){
                    isBuffering=false
                    isReady=true
                    this@PlayVideoViewmodel.isPlaying = true
                }
            }

        })
   }

    fun enterFullScreen(){
        isFullScreen=true
    }

    fun exitFullScreen(){
        isFullScreen=false
    }
    override fun onCleared() {
        super.onCleared()
        player.release()
    }


    fun pause(){
        player.pause()
        isPlaying=false
    }

    fun play(){
        player.play()
        isPlaying=true
    }

    fun setMedia(){
        isReady=false
        currentPosition=0
        duration = 36000
        player.setMediaItem(MediaItem.fromUri("https://res.cloudinary.com/dvtbqqggo/video/upload/v1754030306/qma8aiuo0ebpf6miog0s.mp4"))
    }


    private fun convert(data:Long, index:Int=0, array:MutableList<Long> = ArrayList()):List<Long>{
        if(index == 0){
            val h = data/3600
            if(h.toInt() != 0) array.add(h)
            convert(data-h*3600,1,array)
        }else if( index == 1){
            val h = data/60
            array.add(h)
            convert(data-h*60,2,array)
        }else if(index==2 ){
            array.add(data)
        }
        return array
    }

    fun timeBar(data:Long):String{
        val string= StringBuilder()
        convert(data,0).forEachIndexed {
                index,it->
            if(index==convert(data).size-1) string.append(makeUpNumber(it))
            else string.append(makeUpNumber(it)).append(":")

        }
        return string.toString()
    }

    private fun makeUpNumber(data:Long):String{
        val string= StringBuilder()
        if(data<10) string.append(0).append(data)
        else string.append(data)
        return string.toString()
    }
}