package com.example.movieapp.DetailFimActivity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.ui.theme.*

@Composable
fun FilmItem(item: FilmItemModel, onClick:(FilmItemModel)->Unit){
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable {
                onClick(item)
            }
            .shadow(
                elevation = 8.dp, // độ đổ bóng
                shape = RoundedCornerShape(com.example.movieapp.MovieAppTheme.dimensionValue.roundCornerForTextField),
                clip = false // để bóng không bị cắt bởi clip
            )
            .border(BorderStroke(width=1.dp, color = Color.Black), shape =RoundedCornerShape(com.example.movieapp.MovieAppTheme.dimensionValue.roundCornerForTextField) )
            .clip(RoundedCornerShape(com.example.movieapp.MovieAppTheme.dimensionValue.roundCornerForTextField))
            .background(color = Red)
    ){
        AsyncImage(
            model = item.Poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 140.dp,height = 200.dp)
                .background(Color.Gray)
        )
    }
}