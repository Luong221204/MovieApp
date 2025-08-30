package com.example.movieapp.PlayerActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.ContentScale
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
import com.example.movieapp.DetailFimActivity.About
import com.example.movieapp.DetailFimActivity.DetailMovieViewmodel
import com.example.movieapp.DetailFimActivity.IconChain
import com.example.movieapp.DetailFimActivity.ListGenre
import com.example.movieapp.DetailFimActivity.MoreLikeThis
import com.example.movieapp.DetailFimActivity.TabLayout
import com.example.movieapp.DetailFimActivity.getListTabLayouts
import com.example.movieapp.R
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.ui.theme.BlackGray
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch



class PlayMovieActivity : BaseActivity() {
    @OptIn(UnstableApi::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filmItem: FilmItemModel = (intent.getSerializableExtra("object") as FilmItemModel)
        val intent = Intent("object")
        intent.putExtra("object",filmItem)
        setContent {
            MovieAppTheme{
                val context = LocalContext.current
                val viewmodel: PlayVideoViewmodel = ViewModelProvider(
                    this, PlayMovieFactory(
                        context,filmItem
                    )
                )[PlayVideoViewmodel::class.java]
                ViewScreen(viewmodel, context)
            }

        }
    }


}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(UnstableApi::class)
@Composable
fun ViewScreen(viewModel: PlayVideoViewmodel, context: Context){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(color = colorResource(R.color.blackBackground))
            .navigationBarsPadding()
    ){
        VideoPlayer(viewModel,context)
        Addition(viewModel.filmItemModel.Title,viewModel.filmItemModel.Year,viewModel.filmItemModel.Imdb,modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer3))
        ListGenre(modifier = Modifier.padding(start = MovieAppTheme.paddingDimension.padding3),viewModel.filmItemModel.Genre) { }
        Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer3))
        DescriptionForFilm(modifier = Modifier.padding(start = MovieAppTheme.paddingDimension.padding3),viewModel.filmItemModel.Description)
        Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer3))
        IconChain(modifier = Modifier.fillMaxWidth(MovieAppTheme.alpha.a6))
        Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer5))
        TabLayout(
            modifier = Modifier.height(MovieAppTheme.blockDimension.b60).fillMaxWidth().background(color = colorResource(R.color.black)),
            viewModel,
            getListTabLayouts()
        ){
                viewmodel,page->
            val viewmodel3 = viewmodel as PlayVideoViewmodel
            val searchedFilm = viewmodel3.searchFilms.collectAsState()
            val cast=viewmodel3.listCasts.collectAsState()
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                when(page){
                    0-> Column {
                        MoreLikeThis(searchedFilm.value){}

                    }
                    1-> Column {
                        Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer5))
                        About(cast.value,viewmodel.filmItemModel.Gallery){}
                    }
                }

            }
        }
    }
}

@Composable
fun Imdb(rate:Float,modifier: Modifier){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(MovieAppTheme.viewDimension.v3)
    ) {
        Image(
            painter = painterResource(R.drawable.imdb),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(MovieAppTheme.viewDimension.v3)
                .width(MovieAppTheme.viewDimension.v7)
                .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r2))
        )
        Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer1))

        androidx.compose.material.Text(
            text = "${rate}",
            style =MovieAppTheme.appTypoTheme.t17
        )

    }
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Addition(name:String,year:Int,rate:Float,modifier: Modifier){
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier=Modifier.align(Alignment.CenterStart)
        ){
            Text(text = name,
                style = MovieAppTheme.appTypoTheme.t13
            )
            Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer2))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "$year",
                    style =MovieAppTheme.appTypoTheme.t14
                )
                Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer2))
                Text(text = "USA",
                    modifier = Modifier
                        .background(color = Color.Red.copy(alpha = MovieAppTheme.alpha.a3))
                        .padding(MovieAppTheme.paddingDimension.padding0),
                    style =MovieAppTheme.appTypoTheme.t15
                )
                Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer2))
                Text(text = "4 Seasons",
                    style =MovieAppTheme.appTypoTheme.t16
                )
                Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer2))
                Text(text = "HD",
                    modifier = Modifier
                        .background(color = Color.Red.copy(alpha = MovieAppTheme.alpha.a3))
                        .padding(MovieAppTheme.paddingDimension.padding0),
                    style = MovieAppTheme.appTypoTheme.t15
                )
                Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer2))
                Imdb(rate, modifier = Modifier.padding(start = MovieAppTheme.paddingDimension.padding3))
            }
        }
        Image(
            painter = painterResource(R.drawable.fav),
            contentDescription = null,
            modifier = Modifier
                .size(MovieAppTheme.viewDimension.v7).align(Alignment.CenterEnd)
        )
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
         viewModel.job = launch {
             while (isActive) {
                 if (!isSeeking && isReady) {
                     viewModel.currentPosition = player.currentPosition
                 }
                 delay(200L)
             }
         }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.blackBackground))
            .navigationBarsPadding()
    ) {
        if (!isFullScreen) Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer7))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (isFullScreen) it.fillMaxHeight()
                    else it.aspectRatio(16f / 10f)
                }
                .background(color = colorResource(R.color.blackBackground))

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
                        start = if (isFullScreen) MovieAppTheme.paddingDimension.padding4 else MovieAppTheme.paddingDimension.default,
                        end = if (isFullScreen) MovieAppTheme.paddingDimension.padding12 else MovieAppTheme.paddingDimension.default
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
                        .fillMaxWidth(MovieAppTheme.alpha.a7)
                        .align(Alignment.Center)
                        .padding(bottom = MovieAppTheme.paddingDimension.padding5),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_previous_24),
                            contentDescription = "Rewind",
                            modifier = Modifier.size(MovieAppTheme.iconDimension.i5),
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
                            modifier = Modifier.size(MovieAppTheme.iconDimension.i5),
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { viewModel.setMedia() }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_skip_next_24),
                            contentDescription = "Forward",
                            modifier = Modifier
                                .size(MovieAppTheme.iconDimension.i5)
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
                    .padding(bottom = if (isFullScreen) MovieAppTheme.paddingDimension.padding7 else MovieAppTheme.paddingDimension.default),
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
                                .padding(end = MovieAppTheme.paddingDimension.padding3)
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
                                        .size(MovieAppTheme.iconDimension.i2)
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

    }
}



@Composable
fun DescriptionForFilm(modifier: Modifier,text:String){
    var isExpanded by remember { mutableStateOf(false) }
    val isOver by remember { mutableStateOf(text.length>160) }
    Text(
        lineHeight = 22.sp,
        text = buildAnnotatedString {
            if(!isExpanded && text.length >160) {
                append(text.take(160))
                append(" ...")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)){
                    append("Xem thêm")
                }
            }else{
                append(text)
            }
        },
        style = TextStyle(
            color = Color.White.copy(alpha = if(isExpanded || !isOver) 1f else 0.3f) ,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif
        ),
        modifier = modifier.clickable { isExpanded=!isExpanded }
    )
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
