package com.example.movieapp.CastPackage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.movieapp.BaseActivity
import com.example.movieapp.DetailFimActivity.DetailMovieActivity
import com.example.movieapp.DetailFimActivity.MoreLikeThis
import com.example.movieapp.DetailFimActivity.TabLayout
import com.example.movieapp.DetailFimActivity.TabLayoutItem
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.R
import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel

class CastActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val castModel = intent.getSerializableExtra("cast",CastModel::class.java)
        if(castModel!=null){
            val viewmodel : CastViewmodel = ViewModelProvider(
                this,
                CastViewmodelFactory(castModel)
            )[CastViewmodel::class.java]
            setContent {
                CastScreen(viewmodel,::onFilmClick)
            }
        }
    }
    private fun onFilmClick(filmItemModel: FilmItemModel){
        val intent= Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("object",filmItemModel)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CastScreen(viewmodel: CastViewmodel,onFilmClick:(FilmItemModel)->Unit){
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.blackBackground))
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        Avatar(viewmodel.cast)
        Spacer(modifier = Modifier.height(16.dp))
        TabLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp),
            viewmodel = viewmodel,
            getListTabLayouts()
        ) {
            viewmodel,page->
            val viewmodelBackup = viewmodel as CastViewmodel
            val listFilm by viewmodelBackup.films.collectAsState()
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                when(page){
                    0->{
                        Column(
                        ) {
                           MoreLikeThis(listFilm,onFilmClick)
                        }
                    }
                    1->{
                        Column(modifier = Modifier.padding(top =24.dp)) {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = viewmodelBackup.cast.Bio,
                                style = TextStyle(color = Color.White, fontSize = 14.sp, fontFamily = FontFamily.SansSerif),
                                lineHeight = 22.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            SectionTitle("Gallery",true,null)
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(start = 16.dp)
                            ) {
                                for(link in viewmodel.cast.Gallery){
                                    item {
                                        ActorImage(link)
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
@Composable
fun Avatar(castModel: CastModel){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
    ){
        AsyncImage(
            model = castModel.PicUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.1f),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 64.dp, start = 16.dp)
                .size(32.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(R.color.black2),
                            colorResource(R.color.black1)
                        ), start = Offset(0f, 350f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
        )
        Biography(modifier = Modifier.align(Alignment.Center),castModel)
        SocialMedia(modifier = Modifier.align(Alignment.BottomCenter))
    }
}
@Composable
fun Biography(modifier: Modifier,castModel: CastModel){
    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = castModel.Actor,
            style = TextStyle(color = Color.White.copy(alpha = 0.8f),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        AsyncImage(
            model = castModel.PicUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .border(
                    BorderStroke(
                        width = 0.5.dp, color = colorResource(R.color.blackBackground)
                    ), shape = CircleShape
                )
                .clip(shape = CircleShape)

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "38 movies",
            style = TextStyle(color = Color.White.copy(alpha = 0.8f),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(R.drawable.imdb),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(16.dp)
                    .width(32.dp)
                    .clip(shape = RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Top ${castModel.Top}",
                style = TextStyle(color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "*",
                style = TextStyle(color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = castModel.Born,
                style = TextStyle(color = Color.White,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun SocialMedia(modifier: Modifier){
    Row(modifier = modifier) {
        Media(R.drawable.intagram)
        Spacer(modifier = Modifier.width(24.dp))
        Media(R.drawable.meta)
        Spacer(modifier = Modifier.width(24.dp))
        Media(R.drawable.twitter)
    }
}

@Composable
fun Media(source: Int){
    Box(
        modifier = Modifier
            .size(40.dp)
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
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp)
        )
    }
}

fun getListTabLayouts():List<TabLayoutItem>{
    val list:ArrayList<TabLayoutItem> = ArrayList()
    list.add(TabLayoutItem.Filmography)
    list.add(TabLayoutItem.Biography)
    return list
}

@Composable
fun ActorImage(link:String){
    AsyncImage(
        model = link,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.height(190.dp).width(140.dp).clip(RoundedCornerShape(10.dp))
    )
}