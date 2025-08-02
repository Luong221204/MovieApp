package com.example.movieapp.MainActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieapp.BaseActivity
import com.example.movieapp.MainActivity.BottomNavBar.BottomNavGraph
import com.example.movieapp.MainActivity.BottomNavBar.BottomNavigationBar
import com.example.movieapp.DetailFimActivity.DetailMovieActivity
import com.example.movieapp.DetailFimActivity.FilmItem
import com.example.movieapp.R
import com.example.movieapp.domain.Category
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.ui.theme.MovieAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.KFunction0

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme{
                MainScreen {
                    item->
                    val intent= Intent(this, DetailMovieActivity::class.java)
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

@Composable
fun Preview(){
    MainScreen {  }
}
@Composable
fun MainScreen(onItemClick :(FilmItemModel)->Unit){
    val user=FirebaseAuth.getInstance().currentUser
    val navController= rememberNavController()
    Scaffold(
        modifier = Modifier.padding(WindowInsets.navigationBars.asPaddingValues()),
        bottomBar={
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
                ){
                BottomNavigationBar(link = user?.photoUrl.toString(),navController)
            }
                  },
        backgroundColor = colorResource(R.color.blackBackground)
        )
    {
        paddingValues ->
        Box(
            modifier = Modifier
                .background(color =colorResource(R.color.black1))
        ){

            BottomNavGraph(navController=navController,onItemClick)
        }

    }
}
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun HomeScreen(onItemClick: (FilmItemModel) -> Unit){
    val viewModel : MainViewModel = viewModel()

    val upcoming = viewModel.upcoming
    val newMovie = viewModel.newMovie
    val outstandingMovie = viewModel.outstandingMovie
    val categories = viewModel.category

    val showUpcomingLoading=viewModel.showUpcomingLoading
    val showNewMovieLoading=viewModel.showNewMovieLoading
    val showOutstandingLoading=viewModel.showOutstandingLoading
    val showCategoryLoading=viewModel.showCategoryLoading

    val upcomingFilmsState by viewModel.loadUpcoming.collectAsState()
    val newFilmsState by viewModel.loadItems.collectAsState()
    val mostOutstandingMovieState by viewModel.loadOutstandingMovie.collectAsState()
    val categoriesState by viewModel.loadCategory.collectAsState()

    LaunchedEffect(mostOutstandingMovieState) {
        if(viewModel.isFirstOutstandingMovieLoading!=1) viewModel.addOutstandingMovie(mostOutstandingMovieState)
    }

    LaunchedEffect(newFilmsState) {
        if(viewModel.isFirstNewMovieLoading!=1) viewModel.addNewMovies(newFilmsState)
    }

    LaunchedEffect(upcomingFilmsState) {
        if(viewModel.isFirstUpcomingLoading !=1 ) {
            viewModel.addUpcomingMovies(upcomingFilmsState)
        }
    }

    LaunchedEffect(categoriesState) {
        if(viewModel.isFirstCategoryLoading !=1 ) viewModel.addCategories(categoriesState)

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(R.color.black2))
    ) {
        ShowItemsOnScreen(showOutstandingLoading){
            MostOutStanding(viewModel)
        }
        SectionTitle("New Movies")

        ShowItemsOnScreen(showNewMovieLoading){
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ){
                items(newMovie){
                    item->
                    FilmItem(item,onItemClick)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        SectionTitle("Upcoming Movies")
        Spacer(modifier = Modifier.height(8.dp))
        ShowItemsOnScreen(showUpcomingLoading){
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 10.dp)
            ){
                items(upcoming){
                        item->
                    FilmItem(item,onItemClick)
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        SectionTitle("Categories")
        ShowItemsOnScreen(showCategoryLoading) {
            Categories(categories)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = colorResource(R.color.black1))
        )
    }
}

@Composable
fun ShowItemsOnScreen(showLoading:Boolean, content:@Composable ()-> Unit){
    if(showLoading){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }else{
        content()
    }
}

@Composable
fun SectionTitle(title:String){
    Box(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 16.dp)
        .fillMaxWidth()){
        Text(text = title,
            style = TextStyle(color = Color.White, fontSize = 15.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.TopStart)
        )
        Text(text = "See all",
            style = TextStyle(color = colorResource(R.color.purple_500), fontSize = 12.sp, fontFamily = FontFamily.SansSerif),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}

@Composable
fun Tag(title:String){
    Box(
        modifier= Modifier
            .background(
                color = colorResource(R.color.black2),
                shape = RoundedCornerShape(com.example.movieapp.MovieAppTheme.dimensionValue.roundCornerForTextField)
            )
            .border(
                BorderStroke(
                    width = com.example.movieapp.MovieAppTheme.dimensionValue.borderWidthForTag,
                    brush = Brush.linearGradient(colors = com.example.movieapp.MovieAppTheme.colorScheme.linearGradientColorsForButton)
                ), shape = RoundedCornerShape(com.example.movieapp.MovieAppTheme.dimensionValue.roundCornerForTextField)
            )
            .clip(shape = RoundedCornerShape(com.example.movieapp.MovieAppTheme.dimensionValue.roundCornerForTextField))
    ){
        Text(
            text=title,
            style=TextStyle(fontSize = 10.sp, color = Color.White,fontFamily = FontFamily.Monospace),
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 10.dp)
        )
    }
}

@Composable
fun MostOutStanding(viewModel: MainViewModel){
    val filmItemModel: FilmItemModel=viewModel.outstandingMovie[0]
    val imageOnTop = viewModel.imageOnTop
    val index = viewModel.index
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier= Modifier
                    .height(420.dp)
                    .fillMaxWidth()
            ){
                AsyncImage(
                    model = imageOnTop,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                    alpha = 1.5f
                )
                Box(
                    modifier = Modifier
                        .height(380.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    colorResource(R.color.black1)
                                ), start = Offset(0f, 420f),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            )
                        )
                )
                Text(
                    text =filmItemModel.Title,
                    style = TextStyle(fontSize = 32.sp, color = Color.White, fontFamily = FontFamily.Cursive),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .align(Alignment.BottomCenter)
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ){
                for(i in 0..<filmItemModel.Genre.size){
                    item {
                        Tag(filmItemModel.Genre[i])
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(10.dp),
        ){
            for(i in 0..<filmItemModel.Gallery.size){
                item {
                    if(i ==index) Picture(viewModel,true, link = filmItemModel.Gallery[i],i)
                    else Picture( viewModel = viewModel, link =  filmItemModel.Gallery[i], index = i)
                }
            }
        }
    }



}

@Composable
fun Picture(viewModel: MainViewModel,isSelected:Boolean=false, link:String,index:Int){
    Box(
        modifier = Modifier
        .height(50.dp)
        .width(90.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .clickable{
            viewModel.changeImageOnTop(link,index)
        }
    ){
        AsyncImage(
            model = link,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = if(isSelected) 1f else 0.3f
        )
    }
}

@Composable
fun Categories(list:List<Category>){
    LazyHorizontalGrid(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues( 10.dp),
        rows = GridCells.Fixed(2), modifier = Modifier.height(250.dp) )
    {
        item(span = { GridItemSpan(2) }) {
            Category(list[0])
        }
        for(i in 1..<list.size){
            item{
                Category(list[i])
            }
        }
    }
}


@Composable
fun Category(category: Category){
    Box(
        modifier = Modifier
            .height(0.dp)
            .width(180.dp)
            .clip(shape = RoundedCornerShape(10.dp))

    ){
        AsyncImage(
            model = category.PicUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier.fillMaxSize()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(R.color.black2),
                            colorResource(R.color.black1)
                        ), start = Offset(0f, 100f),
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
        )
        Text(text = category.Name,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 10.dp, start = 20.dp)
            , style = TextStyle(color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontSize = 15.sp,
                )
        )
    }
}
