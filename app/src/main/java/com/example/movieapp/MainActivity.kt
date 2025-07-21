package com.example.movieapp

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.ui.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.ViewModel.MainViewModel
import com.example.movieapp.domain.FilmItemModel
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                Scaffold {
                    MainScreen {  }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GreetingPreview() {
    MovieAppTheme {
        Greeting("Android")
    }
}

@Preview
@Composable
fun Preview(){
    MainScreen {  }
}
@Composable
fun MainScreen(onItemClick :(FilmItemModel)->Unit){
    Scaffold(
        bottomBar={ BottomNavigationBar()},
        floatingActionButton = {
            Box(
                modifier = Modifier.size(MovieAppTheme.dimensionValue.floatingButtonSize)
                    .background(
                        brush = Brush.linearGradient(
                            colors = MovieAppTheme.colorScheme.linearGradientColorsForButton
                        ),
                        shape = CircleShape
                    )
                    .padding(MovieAppTheme.dimensionValue.borderWidthForTextField)
            ){
                FloatingActionButton(
                    onClick = {},
                    backgroundColor = MovieAppTheme.colorScheme.floatingButtonBackgroundColor,
                    modifier = Modifier
                        .size(MovieAppTheme.dimensionValue.floatingButtonSize),
                    contentColor = MovieAppTheme.colorScheme.contentColorForButton,
                    content = {
                        Icon(painter = painterResource(R.drawable.float_icon),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    },

                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        backgroundColor = MovieAppTheme.colorScheme.backGroundColor
        )
    {
        paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = MovieAppTheme.colorScheme.backGroundColor)
        ){
            Image(
                painter = painterResource(id= R.drawable.bg1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
        MainContent {

        }
    }
}
@Composable
fun MainContent(onItemClick: (FilmItemModel) -> Unit){
    val viewModel = MainViewModel()
    val upcoming = remember{ mutableStateListOf<FilmItemModel>()}
    val newMovie = remember{ mutableStateListOf<FilmItemModel>()}
    var showUpcomingLoading by remember { mutableStateOf(true) }
    var showNewMovieLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        viewModel.loadUpcoming().observeForever {
            upcoming.clear()
            upcoming.addAll(it)
            showUpcomingLoading=false
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadItems().observeForever {
            newMovie.clear()
            newMovie.addAll(it)
            showNewMovieLoading=false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 60.dp,bottom=100.dp)
    ) {
        Text(
            text="What would you like to watch ?",
            style = MovieAppTheme.appTypoTheme.textRecommend,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start=16.dp,bottom = 16.dp)
                .fillMaxWidth()
        )
        SearchBar(hint = "Search ..")
        SectionTitle("New Movies")
        if(showNewMovieLoading){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }else{
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ){
                items(newMovie){
                    item->
                    FilmItem(item,onItemClick)
                }
            }
        }
        SectionTitle("Upcoming Movies")
        if(showUpcomingLoading){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            ){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }else{
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ){
                items(upcoming){
                        item->
                    FilmItem(item,onItemClick)
                }
            }
        }
    }
}
@Composable
fun SectionTitle(title:String){
    Text(text = title,
        style = TextStyle(color = Color(0xffffc107), fontSize = 18.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(start = 16.dp,top = 32.dp,bottom = 8.dp)
    )
}