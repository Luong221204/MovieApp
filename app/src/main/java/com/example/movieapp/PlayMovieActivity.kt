package com.example.movieapp

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.example.movieapp.DetailFimActivity.Viewmodel.PlayMovieFactory
import com.example.movieapp.DetailFimActivity.Viewmodel.PlayVideoViewmodel

class PlayMovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context= LocalContext.current
            val viewmodel: PlayVideoViewmodel =ViewModelProvider(
                this, PlayMovieFactory(
                    context
                )
            ) [PlayVideoViewmodel::class.java]
            VideoPlayer(viewmodel,context)
        }
    }


}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(viewModel: PlayVideoViewmodel, context: Context){
    var lifecycle by remember{
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val mediaItem = MediaItem.fromUri("https://res.cloudinary.com/dvtbqqggo/video/upload/v1753693231/gufohbwzevdtqgxfttux.mp4")
    val mediaSource: MediaSource =
        ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(mediaItem)

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver{ _, event ->
            lifecycle=event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        factory = {
            val activity = context as Activity
            val enterFullscreen = { activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE }
            val exitFullscreen = {
                // Will reset to SCREEN_ORIENTATION_USER later
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
            }
            PlayerView(context).also { playerView ->
                playerView.player = viewModel.playVideo()
                playerView.controllerAutoShow=true
                playerView.keepScreenOn=true
                playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                playerView.setFullscreenButtonClickListener {

                        isFullScreen ->
                    with(context) {
                        if (isFullScreen) {
                            if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                                enterFullscreen()
                            }
                        } else {
                            exitFullscreen()
                        }
                    }
                }
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    it.onPause()
                    it.player?.pause()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    it.onResume()
                    it.player?.pause()
                    Log.d("DUCLUONG","release")

                }

                else -> Unit
            }
        }
    )

}