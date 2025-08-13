package com.example.movieapp.PlayerActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.Lifecycle
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.movieapp.BaseActivity
import com.example.movieapp.DetailFimActivity.Viewmodel.PlayMovieFactory
import com.example.movieapp.R
import com.example.movieapp.ui.theme.BlackGray
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlayMovieActivity : BaseActivity() {
    @OptIn(UnstableApi::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current
            val viewmodel: PlayVideoViewmodel = ViewModelProvider(
                this, PlayMovieFactory(
                    context
                )
            )[PlayVideoViewmodel::class.java]
            VideoPlayer(viewmodel, context)
        }
    }


}
@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
fun ViewScreen(){
    Column(){
        Text(text = "Tên phim",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "2022",
                style = TextStyle(
                    fontSize = 11.sp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "USA",
                modifier = Modifier
                    .background(color = Color.Red.copy(alpha = 0.3f))
                    .padding(2.dp),
                style = TextStyle(
                    fontSize = 8.sp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "4 Seasons",
                style = TextStyle(
                    fontSize = 11.sp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "HD",
                modifier = Modifier
                    .background(color = Color.Red.copy(alpha = 0.3f))
                    .padding(2.dp),
                style = TextStyle(
                    fontSize = 8.sp,
                    color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                )
            )
        }

    }
}

@OptIn(UnstableApi::class)
@kotlin.OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ClickableViewAccessibility")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun VideoPlayer(viewModel: PlayVideoViewmodel, context: Context) {
    val scope = rememberCoroutineScope()

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    val isPlaying = viewModel.isPlaying
    val activity = context as Activity

    val player = viewModel.player
    val currentPosition = viewModel.currentPosition
    var current = viewModel.current
    val duration = viewModel.duration
    var isSeeking = viewModel.isSeeking
    val isFullScreen = viewModel.isFullScreen

    val isReady = viewModel.isReady
    val showControls = viewModel.showControls

    var isBuffering = viewModel.isBuffering

    if (isFullScreen) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        activity.window.insetsController?.hide(WindowInsets.Type.systemBars())
        activity.window.insetsController?.systemBarsBehavior =
            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER

    }


    LaunchedEffect(isReady) {
        while (isActive) {
            if (!isSeeking && isReady) {
                viewModel.currentPosition = player.currentPosition
            }
            delay(200L)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.black))
            .navigationBarsPadding()
    ) {
        if (!isFullScreen) Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (isFullScreen) it.fillMaxHeight()
                    else it.aspectRatio(16f / 10f)
                }
                .background(color = colorResource(R.color.black))

        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                viewModel.showControls = true
                                scope.launch {
                                    delay(3000)
                                    viewModel.showControls = false
                                }
                            }
                        )
                    }
                    .let {
                        if (isFullScreen) it.fillMaxHeight()
                        else it.aspectRatio(16f / 9f)
                    }
                    .padding(
                        start = if (isFullScreen) 26.dp else 0.dp,
                        end = if (isFullScreen) 64.dp else 0.dp
                    ),
                factory = {
                    PlayerView(context).also { playerView ->
                        playerView.setShowFastForwardButton(true)
                        playerView.player = player
                        playerView.keepScreenOn = true
                        playerView.useController = false
                        playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM


                    }
                },
                update = {
                    if (lifecycle == Lifecycle.Event.ON_STOP) {
                        viewModel.pause()
                    }
                }
            )
            if (showControls) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .align(Alignment.Center)
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_previous_24),
                            contentDescription = "Rewind",
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        if (isPlaying) viewModel.pause() else viewModel.play()
                    }) {
                        Icon(
                            painter = if (isPlaying) painterResource(R.drawable.baseline_pause_24) else painterResource(
                                R.drawable.baseline_play_arrow_24
                            ),
                            contentDescription = "Forward",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { viewModel.setMedia() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_next_24),
                            contentDescription = "Forward",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.setMedia()

                                },
                            tint = Color.White
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = if (isFullScreen) 32.dp else 0.dp),
            ) {
                if (showControls) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TimeBar(
                            viewModel.timeBar(currentPosition / 1000),
                            viewModel.timeBar(duration / 1000)
                        )
                        Icon(
                            painter = painterResource(R.drawable.baseline_fullscreen_24),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable {
                                    if (isFullScreen) viewModel.exitFullScreen()
                                    else viewModel.enterFullScreen()
                                }
                        )
                    }
                }
                Box(
                ) {
                    Slider(
                        value = if (duration > 0) {
                            currentPosition.toFloat() / duration.toFloat()
                        } else 0f,
                        onValueChange = {
                            isSeeking = true
                            viewModel.currentPosition = (it * duration).toLong()
                        },
                        onValueChangeFinished = {
                            isSeeking = false
                            player.seekTo(currentPosition)
                        },
                        track = {
                            Box(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(color = BlackGray)
                                    .align(Alignment.BottomCenter)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .fillMaxWidth(it.value)
                                        .background(color = Color.Red)
                                        .align(Alignment.BottomStart)
                                )
                            }
                        },
                        thumb = {
                            if (showControls) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .align(Alignment.Center)
                                        .background(color = Color.Red, shape = CircleShape)
                                        .clip(CircleShape)
                                )
                            }
                        }
                    )
                }

            }

        }
        val text1 = "Khi bấm more có thể đổi  để thu gọn lại"+
                "Thêm animation khi mở rộng (animateContentSize())."
        var isExpanded by remember { mutableStateOf(false) }
        var isOver by remember { mutableStateOf(text1.length>160) }

        Spacer(modifier = Modifier.height(8.dp))
        ViewScreen()
        Text(
            lineHeight = 22.sp,
            text = buildAnnotatedString {
                if(!isExpanded && text1.length >160) {
                    append(text1.take(160))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, color = Color.Red)){
                        append("...Xem thêm")
                    }
                }else{
                    append(text1)
                }
            },
            style = TextStyle(
                color = Color.White.copy(alpha = if(isExpanded || !isOver) 1f else 0.3f) ,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.clickable { isExpanded=!isExpanded }
            )
    }
}


@Composable
fun TimeBar(current: String = "", duration: String) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp)
            .background(color = Color.Red.copy(alpha = 0.1f), shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Text(
            text = "$current / $duration",
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            fontSize = 9.sp
        )
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.custom(unit: () -> Float): Modifier {
    return this.fillMaxHeight(unit())
}
