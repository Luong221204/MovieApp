package com.example.movieapp.CastPackage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.decode.ImageSource
import com.example.movieapp.BaseActivity
import com.example.movieapp.R

class CastActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CastScreen()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CastScreen(){
    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.blackBackground)
            )
            .fillMaxSize()
    ) {
        Avatar()
    }
}
@Preview
@Composable
fun Avatar(){
    Box(
        modifier = Modifier.fillMaxWidth().height(380.dp)
    ){
        Image(
            painter = painterResource(R.drawable.film),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.1f),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            modifier = Modifier.padding(top = 64.dp, start = 16.dp).size(35.dp)
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
        Biography(modifier = Modifier.align(Alignment.Center))
        SocialMedia(modifier = Modifier.align(Alignment.BottomCenter))
    }

}
@Preview
@Composable
fun Biography(modifier: Modifier){
    Column(
        modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Avatar",
            style = TextStyle(color = Color.White.copy(alpha = 0.8f),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(R.drawable.film),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .border(BorderStroke(
                    width = 0.5.dp, color = colorResource(R.color.blackBackground)
                ), shape = CircleShape)
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
                modifier = Modifier.height(16.dp).width(32.dp).clip(shape = RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Top 12",
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
                text = "Actor, Producer",
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
        modifier = Modifier.size(40.dp)
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
            modifier = Modifier.align(Alignment.Center).size(24.dp)
        )
    }
}