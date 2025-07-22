package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.AsyncImage
import com.example.movieapp.domain.FilmItemModel

class DetailMovieActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var filmItem:FilmItemModel = (intent.getSerializableExtra("object")as FilmItemModel)
        setContent {
            DetailMovieScreen(filmItem, onBackClick = {finish()})
        }
    }
}
@Composable
fun DetailMovieScreen(film:FilmItemModel,onBackClick:()->Unit){
    val scrollState= rememberScrollState()
    val isLoading by remember{ mutableStateOf(false)}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.blackBackground))

    ) {
        if(isLoading){
            CircularProgressIndicator()
        }else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)

            ){
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
                        contentDescription = null
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
                        style = TextStyle(color = Color.White, fontSize = 27.sp),
                        modifier = Modifier
                            .padding(end = 16.dp, top = 48.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.star),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(text ="IMDB: ${(film.Imdb)}",color = Color.White)

                            Icon(
                                painter = painterResource(R.drawable.time),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(text ="Runtime: ${(film.Time)}",color = Color.White)

                            Icon(
                                painter = painterResource(R.drawable.cal),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            Text(text ="Release: ${(film.Year)}",color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(text="Summary",
                            style = TextStyle(color = Color.White, fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text=film.Description,
                            style = TextStyle(color = Color.White, fontSize = 14.sp))
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(text="Actors",
                            style = TextStyle(color = Color.White, fontSize = 16.sp))
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(contentPadding = PaddingValues(8.dp)) {
                            items(film.Casts.size){
                                film.Casts[it].Actor?.let{
                                    Text(
                                        text = "$it, ",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                        LazyRow(contentPadding = PaddingValues(8.dp)) {
                            items(film.Casts.size){
                                AsyncImage(
                                    model=film.Casts[it].PicUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(75.dp)
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(50.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }


            }
        }
    }

}