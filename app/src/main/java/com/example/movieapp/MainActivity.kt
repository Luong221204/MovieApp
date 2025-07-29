package com.example.movieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.ViewModel.MainViewModel
import com.example.movieapp.domain.FilmItemModel
import com.example.movieapp.ui.theme.Black3
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.Pink
import com.example.movieapp.ui.theme.Red
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme{
                MainScreen {
                    item->
                    val intent= Intent(this,DetailMovieActivity::class.java)
                    intent.putExtra("object",item)
                    startActivity(intent)
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
    val user=FirebaseAuth.getInstance().currentUser
    val navController= rememberNavController()
    Scaffold(
        bottomBar={
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
                ){
                BottomNavigationBar(link = user?.photoUrl.toString(),navController)
            }
                  },
        floatingActionButton = {
            Box(
                modifier = Modifier.size(60.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Pink, Red)
                        ),
                        shape = CircleShape
                    )
                    .padding(3.dp)
            ){
                FloatingActionButton(
                    onClick = {},
                    backgroundColor = Black3,
                    modifier = Modifier
                        .size(58.dp),
                    contentColor = Color.White,
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
        backgroundColor = colorResource(R.color.blackBackground)
        )
    {
        paddingValues ->
        Box(
            modifier = Modifier
                .background(color =colorResource(R.color.blackBackground))
        ){
            Image(
                painter = painterResource(id= R.drawable.bg1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            BottomNavGraph(navController=navController,onItemClick)
        }

    }
}
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun HomeScreen(onItemClick: (FilmItemModel) -> Unit){
    val viewModel : MainViewModel= viewModel()
    val upcoming = viewModel.upcoming
    val newMovie = viewModel.newMovie
    val showUpcomingLoading=viewModel.showUpcomingLoading
    val showNewMovieLoading=viewModel.showNewMovieLoading
    val upcomingFilmsState by viewModel.loadUpcoming.collectAsState()
    val newFilmsState by viewModel.loadItems.collectAsState()
    LaunchedEffect(newFilmsState) {
        if(viewModel.isFirstNewMovieLoading!=1) viewModel.addNewMovies(newFilmsState)
    }

    LaunchedEffect(upcomingFilmsState) {
        if(viewModel.isFirstUpcomingLoading !=1 ) viewModel.addUpcomingMovies(upcomingFilmsState)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 40.dp,bottom=0.dp)
    ) {
        SearchBar(hint = "")
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
        modifier = Modifier.padding(start = 16.dp,top = 16.dp,bottom = 8.dp)
    )
}
fun cong():Int{ return 10}