package com.example.movieapp.DetailFimActivity

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.movieapp.BaseActivity
import com.example.movieapp.CastPackage.CastActivity
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.MainActivity.Tag
import com.example.movieapp.GenreBottom.OnShowBottomSheet
import com.example.movieapp.PlayerActivity.DescriptionForFilm
import com.example.movieapp.PlayerActivity.PlayMovieActivity
import com.example.movieapp.R
import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.domain.FilmItemModel.Trailer
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.launch
import java.io.File

class DetailMovieActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filmItem: FilmItemModel = (intent.getSerializableExtra("object") as FilmItemModel)
        val intent = Intent("object")
        intent.putExtra("object",filmItem)
        sendBroadcast(intent)
        val viewmodel: DetailMovieViewmodel = ViewModelProvider(
            this,
            DetailMovieFactory(filmItem)
        )[DetailMovieViewmodel::class.java]
        setContent {
            MovieAppTheme {
                DetailMovieScreen(viewmodel, onBackClick = { finish()}, onFilmClick = ::onFilmClick, onToPlayMovie = ::onPlayMovie){
                        castModel->
                    val intent = Intent(this,CastActivity::class.java)
                    intent.putExtra("cast",castModel)
                    startActivity(intent)
                }
            }
           
        }
    }
    private fun onFilmClick(filmItemModel: FilmItemModel){
        val intent= Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("object",filmItemModel)
        startActivity(intent)
    }

    private fun onPlayMovie(filmItemModel: FilmItemModel){
        val intent= Intent(this, PlayMovieActivity::class.java)
        intent.putExtra("object",filmItemModel)
        startActivity(intent)
    }

}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovieScreen(
    viewmodel: DetailMovieViewmodel,
    onBackClick: () -> Unit,
    onFilmClick: (FilmItemModel) -> Unit,
    onToPlayMovie:(FilmItemModel)->Unit,
    onCastClick :(CastModel)->Unit,
    ) {
    val scrollState = rememberScrollState()

    var showDialog by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val isShowBottomSheet = viewmodel.isShowBottomSheet
    val showBottomSheet:(String)->Unit ={
        data->
        coroutineScope.launch {
            sheetState.show()
        }.invokeOnCompletion { viewmodel.onOpenStatusBottomSheet(data) }
    }
    val hideBottomSheet:()->Unit ={
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion { viewmodel.onCloseStatusBottomSheet() }
    }

    val tag =viewmodel.tag

    //dialog
    if(showDialog){
        PlayTrailer(onDismiss = {showDialog=false},viewmodel.filmItemModel.Trailer)
    }
    OnShowBottomSheet(tag,isShowBottomSheet,sheetState,hideBottomSheet)
    val film = viewmodel.filmItemModel
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.blackBackground))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onToPlayMovie(film)
                }
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier.height(MovieAppTheme.blockDimension.b40)
            ) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start =  MovieAppTheme.paddingDimension.padding3, top =  MovieAppTheme.paddingDimension.padding12)
                        .size(MovieAppTheme.viewDimension.v7)
                        .clickable { onBackClick() }
                )
                Image(
                    painter = painterResource(R.drawable.fav),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end =  MovieAppTheme.paddingDimension.padding3, top =  MovieAppTheme.paddingDimension.padding12)
                        .size(MovieAppTheme.viewDimension.v7)
                        .align(Alignment.TopEnd)
                )
                AsyncImage(
                    model = film.Poster,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    alpha =  MovieAppTheme.alpha.a1
                )
                AsyncImage(
                    model = film.Poster,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(MovieAppTheme.blockDimension.b21,  MovieAppTheme.blockDimension.b30)
                        .clip(RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r30))
                        .align(Alignment.BottomCenter),
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .height(MovieAppTheme.blockDimension.b10)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(R.color.black2),
                                    colorResource(R.color.black1)
                                ), start = Offset(0f, 0f),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            )
                        )
                )
                Text(
                    text = film.Title,
                    style =MovieAppTheme.appTypoTheme.t6,
                    modifier = Modifier
                        .padding(end = MovieAppTheme.paddingDimension.padding3, top =  MovieAppTheme.paddingDimension.padding11)
                        .align(Alignment.BottomCenter)
                )
            }
            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
            Column(modifier = Modifier.padding(horizontal = MovieAppTheme.paddingDimension.padding3)) {
                IconChain(modifier = Modifier.padding(horizontal = MovieAppTheme.paddingDimension.padding9).fillMaxWidth())
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
                Caption(filmItemModel = film)
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
                ListGenre(modifier = Modifier,film.Genre,showBottomSheet)
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))

                DescriptionForFilm(modifier = Modifier,film.Description)

                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
                Teaser(film.Trailer){
                    showDialog=true
                }
            }
            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
            TabLayout(
                modifier = Modifier.height(MovieAppTheme.blockDimension.b60).fillMaxWidth(),
                viewmodel,
                getListTabLayouts()
            ){
                viewmodel,page->
                val viewmodel3 = viewmodel as DetailMovieViewmodel
                val searchedFilm = viewmodel3.searchedFilm.collectAsState()
                val cast=viewmodel3.listCasts.collectAsState()
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopStart
                ) {
                    when(page){
                        0-> Column {
                            MoreLikeThis(searchedFilm.value,onFilmClick)

                        }
                        1-> Column {
                            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
                            About(cast.value,viewmodel.filmItemModel.Gallery, onClick = onCastClick)
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun ListGenre(modifier: Modifier,list: List<String>,showBottomSheet:(String)->Unit){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding3)
    ) {
        items(list){
            Tag(showBottomSheet,it)
        }
    }
}
@Composable
fun FilmIcon(source:Int,onClick:()->Unit){
    Box(
        modifier = Modifier.size(MovieAppTheme.viewDimension.v7)
            .clickable { onClick() }
            .background(
                color = colorResource(R.color.red).copy(alpha = MovieAppTheme.alpha.a1/2),
                shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10)
            )
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10))
    ){
        Icon(
            painter = painterResource(source),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.align(Alignment.Center).size(MovieAppTheme.viewDimension.v3)
        )
    }
}

@Composable
fun IconChain(modifier: Modifier){
    val context = LocalContext.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FilmIcon(R.drawable.download) {

        }
        FilmIcon(R.drawable.save2) { }
        FilmIcon(R.drawable.send) {

            val shareText = "https://youtu.be/abc123"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
        FilmIcon(R.drawable.more) { }

    }
}

@Composable
fun Caption(filmItemModel: FilmItemModel){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PartialCaption("${filmItemModel.Imdb}",R.drawable.star)
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
        PartialCaption(filmItemModel.Time,R.drawable.time)
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
        PartialCaption("${filmItemModel.Year}",R.drawable.cal)
    }
}

@Composable
fun PartialCaption(text:String,source:Int){
    Icon(
        painter = painterResource(source),
        contentDescription = null,
        tint = Color.Red,
        modifier = Modifier.padding(end = MovieAppTheme.paddingDimension.padding1).size(MovieAppTheme.iconDimension.i2)
    )
    Text(text = text,
        style = MovieAppTheme.appTypoTheme.t7
    )
}

@Composable
fun Teaser(trailer:Trailer,onClick: () -> Unit){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .background(color = colorResource(R.color.black1), shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10)),
    ){
        Box(
            modifier = Modifier.weight(1f)
        ){
            Video(trailer.Image, modifier = Modifier.clickable { onClick() })
        }

        Box(
            modifier = Modifier.weight(1f)
        ){
            Column(modifier = Modifier.padding(MovieAppTheme.paddingDimension.padding3)) {
                Text(
                    text = trailer.Name,
                    style =MovieAppTheme.appTypoTheme.t8
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer2))
                Text(text = trailer.TimeString(),
                    style = MovieAppTheme.appTypoTheme.t9)
            }

        }
    }
}


@Composable
fun Video(link:String,modifier: Modifier){
    Box(modifier = modifier){
        AsyncImage(
            model = link,
            contentScale=ContentScale.Crop,
            modifier = Modifier
                .alpha( MovieAppTheme.alpha.a7)
                .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10))
                .height(MovieAppTheme.blockDimension.b10).aspectRatio(15/9f),
            contentDescription = null
        )
        Icon(
            painter = painterResource(R.drawable.play),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size( MovieAppTheme.viewDimension.v7)
                .align(Alignment.Center)
                .shadow(elevation = MovieAppTheme.blockDimension.b10)
                .padding(end = MovieAppTheme.paddingDimension.padding1)
        )
    }

}
fun getListTabLayouts():List<TabLayoutItem>{
    val list:ArrayList<TabLayoutItem> = ArrayList()
    list.add(TabLayoutItem.MoreLikeThis)
    list.add(TabLayoutItem.About)

    return list
}

@Composable
fun MoreLikeThis(list:List<FilmItemModel>,onClick: (FilmItemModel) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
        verticalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
        contentPadding = PaddingValues(horizontal = MovieAppTheme.paddingDimension.padding3, vertical = MovieAppTheme.paddingDimension.padding5)
    ) {
        for(element in list){
            item {
                FilmItem2(element,onClick)
            }
        }

    }


}

@Composable
fun About(list:List<CastModel>, listLinks:List<String> , onClick: (CastModel) -> Unit){
    Column(
    ) {
        Text(
            modifier = Modifier.padding(horizontal = MovieAppTheme.paddingDimension.padding3),
            text = R.string.cast_crew.toString(),
            style= MovieAppTheme.appTypoTheme.t10
        )
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
        LazyRow(
            contentPadding = PaddingValues(horizontal = MovieAppTheme.paddingDimension.padding3),
            horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1)
        ) {
            for(i in list.indices){
                item{
                    Cast(list[i],onClick=onClick)
                }
            }
        }
        SectionTitle(R.string.gallery.toString(),true,null)
        LazyRow(
            contentPadding = PaddingValues(horizontal = MovieAppTheme.paddingDimension.padding3),
            horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1)
        ) {
            for (i in listLinks.indices){
                item {
                    Gallery(listLinks[i])
                }
            }
        }
    }

}



@Composable
fun Cast(castModel: CastModel,onClick: (CastModel) -> Unit){
    Row(
        modifier = Modifier.height(MovieAppTheme.blockDimension.b8).width(MovieAppTheme.blockDimension.b15).clickable { onClick(castModel) },
        verticalAlignment = Alignment.CenterVertically
        ,horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding2)) {
        AsyncImage(
            model = castModel.PicUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(MovieAppTheme.blockDimension.b7)
        )
        Text(
            text = castModel.Actor,
            maxLines = 3,
            style = MovieAppTheme.appTypoTheme.t11
        )
    }
}
@Composable
fun FilmItem2(filmItemModel: FilmItemModel,onClick: (FilmItemModel) -> Unit){
    Box(modifier = Modifier
        .height(MovieAppTheme.blockDimension.b23)
        .width(MovieAppTheme.blockDimension.b18)
        .clickable { onClick(filmItemModel) }
        .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10))) {
        AsyncImage(
            model = filmItemModel.Poster,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(MovieAppTheme.paddingDimension.padding2)
                .align(Alignment.TopStart)
                .height(MovieAppTheme.blockDimension.b2)
                .width(MovieAppTheme.blockDimension.b5)
                .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r5))
                .background(
                    color = Color.Red.copy(alpha = MovieAppTheme.alpha.a1),
                    shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r5)
                )
        ) {
            Image(
                painter = painterResource(R.drawable.imdb),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(MovieAppTheme.blockDimension.b1)
                    .width(MovieAppTheme.blockDimension.b2)
                    .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r2))
            )
            Text(text = "${filmItemModel.Imdb}", style =MovieAppTheme.appTypoTheme.t12)
        }
    }
}
@Composable
fun Gallery(link:String){
    AsyncImage(
        model = link,
        contentScale=ContentScale.Crop,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10))
            .height(MovieAppTheme.blockDimension.b10).aspectRatio(15/9f),
        contentDescription = null
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun PlayTrailer(onDismiss:()->Unit,trailer: Trailer?,context:Context = LocalContext.current){
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver {_,event->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycle = Lifecycle.Event.ON_DESTROY
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }



    val player: Player = ExoPlayer.Builder(context).build().apply {
        playWhenReady = true
        val mediaItem = MediaItem.Builder()
            .setUri(trailer?.Video)
            .setMimeType(MimeTypes.VIDEO_MP4)
            .build()
        setMediaItem(mediaItem)
        prepare()
    }
    Dialog(
        onDismissRequest = {
            onDismiss()
            player.release()
        },
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth().aspectRatio(16f/10f),
            factory = {
                PlayerView(context).also { playerView ->
                    playerView.setShowFastForwardButton(true)
                    playerView.player = player
                    playerView.keepScreenOn = true
                    playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                    playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            update = {
                if(lifecycle == Lifecycle.Event.ON_PAUSE) player.pause()
                if(lifecycle == Lifecycle.Event.ON_DESTROY) player.release()
            }
        )
    }
}