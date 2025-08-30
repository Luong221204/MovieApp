package com.example.movieapp.DetailFimActivity

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.movieapp.R
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.ui.theme.*

@Composable
fun FilmItem(item: FilmItemModel, onClick:(FilmItemModel)->Unit){
    var isSuccess by remember{
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .width(MovieAppTheme.blockDimension.b14)
            .clickable {
                onClick(item)
            }
            .shadow(
                elevation =MovieAppTheme.dimensionValue.e1,
                shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20),
                clip = false
            )
            .border(BorderStroke(width=MovieAppTheme.thinDimension.t2, color = Color.Black), shape =RoundedCornerShape(
                MovieAppTheme.roundedCornerDimension.r20) )
            .clip(RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .background(color = Red)
    ){

        AsyncImage(
            model = item.Poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = MovieAppTheme.blockDimension.b14,height = MovieAppTheme.blockDimension.b20)
                .background(Color.Gray),
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
            Text(text = "${item.Imdb}", style = MovieAppTheme.appTypoTheme.t12)
        }
    }
}