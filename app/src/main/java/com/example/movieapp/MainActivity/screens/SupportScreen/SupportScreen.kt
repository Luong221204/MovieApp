package com.example.movieapp.MainActivity.screens.SupportScreen

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.MainActivity.screens.ExploreScreen.CustomForEach
import com.example.movieapp.MainActivity.screens.ExploreScreen.ItemsInRow
import com.example.movieapp.R
import com.example.movieapp.ui.theme.BlackGray


@Composable
fun SupportScreen(
    viewmodel: SupportViewmodel
) {
    val history=viewmodel.history.collectAsState()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(color = colorResource(R.color.blackBackground)),
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        ButtonForMyList("Download",R.drawable.download) { }
        Spacer(modifier = Modifier.height(20.dp))
        ButtonForMyList("Favourite",R.drawable.save2) { }
        Spacer(modifier = Modifier.height(8.dp))
        SectionTitle("History",false) { }
        Text("The last 10 movies you watched will be here",
            fontSize = 11.sp, color = Color.White, fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        history.value.CustomForEach(2){
                list->
            ItemsInRow(viewmodel.convertListFilm(list))
            Spacer(modifier = Modifier.height(8.dp))
        }
        Box(modifier = Modifier.height(100.dp).background(color = colorResource(R.color.blackBackground)))

    }
}

@Composable
fun ButtonForMyList(content:String,source:Int,onClick:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
            .background(color = BlackGray, shape = RoundedCornerShape(15.dp))
            .border(BorderStroke(width = 0.5.dp, color = Color.Red.copy(0.7f)), shape =RoundedCornerShape(15.dp))
            .clip(shape = RoundedCornerShape(15.dp) )
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.TopStart).fillMaxHeight().padding(start = 16.dp)
        ){
            Icon(
                painter = painterResource(source),
                contentDescription = null,
                tint = Color.Red.copy(alpha = 0.8f),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            androidx.compose.material3.Text(
                text = content,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Icon(
            painter = painterResource(R.drawable.a_right),
            contentDescription = null,
            tint = Color.Red.copy(alpha = 0.8f),
            modifier = Modifier.size(28.dp).align(Alignment.CenterEnd).padding(end = 16.dp)
        )
    }
}