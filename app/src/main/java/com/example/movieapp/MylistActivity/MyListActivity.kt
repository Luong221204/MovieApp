package com.example.movieapp.MylistActivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.BaseActivity
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme

class MyListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DownloadScreen()
        }
    }
}
@Composable
fun DownloadScreen(){
    Column(
        modifier = Modifier.background(color = colorResource(R.color.blackBackground)).fillMaxSize()
    ) {
        TopBarTitle(R.string.download.toString()) { }
        DownloadItem()
    }
}
@Composable
fun TopBarTitle(title:String,onBackClick:()->Unit){
    Box(
        modifier = Modifier.padding(top = MovieAppTheme.paddingDimension.padding11, bottom = MovieAppTheme.paddingDimension.padding0).fillMaxWidth()
    ){
        Image(
            painter = painterResource(R.drawable.back),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.padding(start = MovieAppTheme.paddingDimension.padding3).size(MovieAppTheme.viewDimension.v7).clickable { onBackClick() }.align(Alignment.TopStart)
        )
        Text(
            text = title,
            style = MovieAppTheme.appTypoTheme.t13,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun DownloadItem(){
    Row {
        Image(
            painter = painterResource(R.drawable.film),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.width(MovieAppTheme.blockDimension.b8).fillMaxHeight()
        )
    }
    Row(
        modifier = Modifier.height(MovieAppTheme.blockDimension.b17).fillMaxWidth()
            .padding(16.dp)
            .border(BorderStroke(width = 1.dp, color = Color.Red.copy(alpha = MovieAppTheme.alpha.a8),), shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r16))
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r15))

    ) {

        Column(modifier = Modifier.padding(MovieAppTheme.paddingDimension.padding3).width(MovieAppTheme.blockDimension.b24)) {
            Text(text = "Avatar 2 :The way of water ",
                style = MovieAppTheme.appTypoTheme.t18
            )
            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
            Text(text = "Avatar 2 :The way of water",
                style = MovieAppTheme.appTypoTheme.t18
            )
        }
        Icon(
            painter = painterResource(R.drawable.save2),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.padding(vertical = MovieAppTheme.paddingDimension.padding3).size(MovieAppTheme.iconDimension.i4)
        )
    }
}