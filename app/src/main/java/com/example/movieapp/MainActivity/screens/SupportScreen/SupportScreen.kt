package com.example.movieapp.MainActivity.screens.SupportScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.MainActivity.screens.ExploreScreen.CustomForEach
import com.example.movieapp.MainActivity.screens.ExploreScreen.ItemsInRow
import com.example.movieapp.MylistActivity.MyListActivity
import com.example.movieapp.R
import com.example.movieapp.ui.theme.BlackGray
import com.example.movieapp.ui.theme.MovieAppTheme


@Composable
fun SupportScreen(
    viewmodel: SupportViewmodel,
    context:Context= LocalContext.current
) {
    val history=viewmodel.history.collectAsState()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(color = colorResource(R.color.blackBackground)),
    ) {
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer9))
        ButtonForMyList(R.string.download.toString(),R.drawable.download) {
            val intent = Intent(context,MyListActivity::class.java)
            context.startActivity(intent)
        }
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
        ButtonForMyList(R.string.favourite.toString(),R.drawable.save2) { }
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
        SectionTitle(R.string.history.toString(),false) {

        }
        Text(
            R.string.last_10.toString(),
            style = MovieAppTheme.appTypoTheme.t16,
            modifier = Modifier.padding(start = MovieAppTheme.paddingDimension.padding3)
        )
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
        history.value.CustomForEach(2){
                list->
            ItemsInRow(viewmodel.convertListFilm(list))
            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
        }
        Box(modifier = Modifier.height(MovieAppTheme.blockDimension.b10).background(color = colorResource(R.color.blackBackground)))

    }
}

@Composable
fun ButtonForMyList(content:String,source:Int,onClick:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MovieAppTheme.blockDimension.b6)
            .clickable { onClick() }
            .padding(horizontal = MovieAppTheme.paddingDimension.padding3)
            .background(color = BlackGray, shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r15))
            .border(BorderStroke(width =MovieAppTheme.thinDimension.t1, color = Color.Red.copy(MovieAppTheme.alpha.a7)), shape =RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r15))
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r15) )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.TopStart).fillMaxHeight().padding(start = MovieAppTheme.paddingDimension.padding3)
        ){
            Icon(
                painter = painterResource(source),
                contentDescription = null,
                tint = Color.Red.copy(alpha = MovieAppTheme.alpha.a8),
                modifier = Modifier.size(MovieAppTheme.iconDimension.i5)
            )
            Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer3))
            Text(
                text = content,
                style = MovieAppTheme.appTypoTheme.t23
            )
        }
        Icon(
            painter = painterResource(R.drawable.a_right),
            contentDescription = null,
            tint = Color.Red.copy(alpha =MovieAppTheme.alpha.a8),
            modifier = Modifier.size(MovieAppTheme.iconDimension.i6).align(Alignment.CenterEnd).padding(end = MovieAppTheme.paddingDimension.padding3)
        )
    }
}