package com.example.movieapp.DetailFimActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.movieapp.BaseActivity
import com.example.movieapp.MainActivity.Tag
import com.example.movieapp.R
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.domain.FilmItemModel.Trailer

class DetailMovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filmItem: FilmItemModel = (intent.getSerializableExtra("object") as FilmItemModel)
        val viewmodel: DetailMovieViewmodel = ViewModelProvider(
            this,
            DetailMovieFactory(filmItem)
        )[DetailMovieViewmodel::class.java]
        setContent {
            DetailMovieScreen(viewmodel, onBackClick = { finish() })
        }
    }
}

@Composable
fun DetailMovieScreen(viewmodel: DetailMovieViewmodel, onBackClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val film = viewmodel.filmItemModel
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.blackBackground))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier.height(400.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 48.dp)
                        .clickable { onBackClick() }
                )
                Image(
                    painter = painterResource(R.drawable.fav),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp, top = 48.dp)
                        .align(Alignment.TopEnd)
                )
                AsyncImage(
                    model = film.Poster,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    alpha = 0.1f
                )
                AsyncImage(
                    model = film.Poster,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(210.dp, 300.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .align(Alignment.BottomCenter),
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .height(100.dp)
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
                    style = TextStyle(color = Color.White, fontSize = 32.sp, fontFamily = FontFamily.Cursive),
                    modifier = Modifier
                        .padding(end = 16.dp, top = 48.dp)
                        .align(Alignment.BottomCenter)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                IconChain()
                Spacer(modifier = Modifier.height(16.dp))
                Caption(filmItemModel = film)
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(film.Genre){
                        Tag(it)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    lineHeight = 22.sp,
                    text = film.Description,
                    style = TextStyle(color = Color.White, fontSize = 14.sp, fontFamily = FontFamily.SansSerif),

                )
                Spacer(modifier = Modifier.height(24.dp))
                Teaser(film.Trailer)
            }
            Spacer(modifier = Modifier.height(24.dp))
            TabLayout(modifier = Modifier.height(600.dp).fillMaxWidth(),viewmodel)

        }

    }

}

@Composable
fun FilmIcon(source:Int,onClick:()->Unit){
    Box(
        modifier = Modifier.size(32.dp)
            .background(
                color = colorResource(R.color.red).copy(alpha = 0.05f),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
    ){
        Icon(
            painter = painterResource(source),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.align(Alignment.Center).size(16.dp)
        )
    }
}

@Composable
fun IconChain(){
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FilmIcon(R.drawable.download) { }
        FilmIcon(R.drawable.save2) { }
        FilmIcon(R.drawable.send) { }
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
        Spacer(modifier = Modifier.width(16.dp))
        PartialCaption(filmItemModel.Time,R.drawable.time)
        Spacer(modifier = Modifier.width(16.dp))
        PartialCaption("${filmItemModel.Year}",R.drawable.cal)
    }
}

@Composable
fun PartialCaption(text:String,source:Int){
    Icon(
        painter = painterResource(source),
        contentDescription = null,
        tint = Color.Red,
        modifier = Modifier.padding(end = 8.dp).size(13.dp)
    )
    Text(text = text, color = Color.White,
        style = TextStyle(fontFamily = FontFamily.SansSerif,
            fontSize = 12.sp)
    )
}

@Composable
fun Teaser(trailer:Trailer){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .background(color = colorResource(R.color.black1), shape = RoundedCornerShape(10.dp)),
    ){
        Box(
            modifier = Modifier.weight(1f)
        ){
            AsyncImage(
                model = trailer.Image,
                contentScale=ContentScale.Crop,
                modifier = Modifier
                    .alpha(0.7f)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .height(105.dp).aspectRatio(15/9f),
                contentDescription = null
            )
            Icon(
                painter = painterResource(R.drawable.play),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
                    .shadow(elevation = 10.dp)
                    .padding(end = 8.dp)
            )
        }

        Box(
            modifier = Modifier.weight(1f)
        ){
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    lineHeight = 19.sp,
                    text = trailer.Name,color = Color.White,
                    style = TextStyle(fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = trailer.Time,color = Color.White,
                    style = TextStyle(fontFamily = FontFamily.SansSerif,
                        fontSize = 10.sp))
            }

        }
    }
}

