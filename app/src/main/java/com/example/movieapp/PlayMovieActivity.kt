package com.example.movieapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.Lifecycle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.example.movieapp.DetailFimActivity.Viewmodel.PlayMovieFactory
import com.example.movieapp.DetailFimActivity.Viewmodel.PlayVideoViewmodel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayMovieActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
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

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalMaterial3Api::class)

@SuppressLint("ClickableViewAccessibility")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun VideoPlayer(viewModel: PlayVideoViewmodel, context: Context){
    val scope = rememberCoroutineScope()


    var isBuffering by remember {
        mutableStateOf(true)
    }
    var lifecycle by remember{
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val mediaItem = MediaItem.fromUri("https://res.cloudinary.com/dvtbqqggo/video/upload/v1754030306/qma8aiuo0ebpf6miog0s.mp4")

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
    var isPlaying by remember { mutableStateOf(true) }
    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.video)

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
        }
    }
    var showControls by remember { mutableStateOf(false) }
    player.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            if (state == Player.STATE_ENDED) {
                isPlaying=false
                showControls=true
            }else if(state == Player.STATE_READY){
                isBuffering=false
            }
        }
    })
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    var isSeeking by remember { mutableStateOf(false) }
    var isSliding by remember { mutableStateOf(false) }


    // Lắng nghe thay đổi tiến trình
    LaunchedEffect(player) {

        while (true) {
            if (!isSeeking) {
                currentPosition = player.currentPosition
                duration = player.duration.coerceAtLeast(0L)

            }
            delay(1000L) // Cập nhật mỗi 0.5s
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.black))
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f/10f)
                .background(color = colorResource(R.color.black))
        ){
            if(isBuffering){
                CircularLoginProcess()
            }
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit){
                        detectTapGestures(
                            onTap = {
                                showControls=true
                                scope.launch{
                                    delay(3000)
                                    showControls=false
                                }
                            }
                        )
                    }
                    .aspectRatio(16f / 9f),
                factory = {
                    val activity = context as Activity
                    val enterFullscreen = { activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER }
                    val exitFullscreen = {
                        // Will reset to SCREEN_ORIENTATION_USER later
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                    }
                    PlayerView(context).also { playerView ->
                        playerView.player =player
                        playerView.controllerAutoShow=true
                        playerView.keepScreenOn=true
                        playerView.useController=false
                        playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    }
                },
            )
            if(showControls){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { player.seekTo(player.currentPosition - 10_000) }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_previous_24),
                            contentDescription = "Rewind",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White)
                    }
                    IconButton(onClick = {
                        if (isPlaying) player.pause() else player.play()
                        isPlaying = !isPlaying
                    }) {
                        Icon(
                            painter = if(isPlaying)painterResource(R.drawable.baseline_pause_24) else painterResource(R.drawable.baseline_play_arrow_24) ,
                            contentDescription = "Forward",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White)
                    }
                    IconButton(onClick = { player.seekTo(player.currentPosition + 10_000) }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_next_24),
                            contentDescription = "Forward",
                            modifier = Modifier.size(24.dp).clickable {
                                val skipMs = 10_000L // 10 giây
                                val target = (player.currentPosition + skipMs).coerceAtMost(player.duration)
                                player.seekTo(target)

                                                                      },
                            tint = Color.White)
                    }
                }
            }

        }
    }

    }


