package com.example.movieapp

import android.content.ClipData.Item
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movieapp.domain.FilmItemModel
import com.example.movieapp.ui.theme.*

@Composable
fun FilmItem(item:FilmItemModel,onClick:(FilmItemModel)->Unit){
    Column(
        modifier = Modifier
            .padding(4.dp)
            .width(120.dp)
            .clickable {
                onClick(item)
            }
            .shadow(
                elevation = 8.dp, // độ đổ bóng
                shape = RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField),
                clip = false // để bóng không bị cắt bởi clip
            )
            .border(BorderStroke(width=1.dp, color = Color.Black), shape =RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField) )
            .clip(RoundedCornerShape(MovieAppTheme.dimensionValue.roundCornerForTextField))
            .background(color = Red)
    ){
        AsyncImage(
            model = item.Poster,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(width = 120.dp,height = 150.dp)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.Title ,
            modifier = Modifier.padding(start = 4.dp),
            style = TextStyle(color = Color.White , fontSize = 11.sp),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp,bottom = 4.dp)
        ){
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Color(0xffffc107)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = item.Imdb.toString(),style = TextStyle(color =Color.White, fontSize = 12.sp))
        }
    }
}