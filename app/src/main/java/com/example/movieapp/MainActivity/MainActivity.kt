package com.example.movieapp.MainActivity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieapp.BaseActivity
import com.example.movieapp.MainActivity.BottomNavBar.BottomNavGraph
import com.example.movieapp.MainActivity.BottomNavBar.BottomNavigationBar
import com.example.movieapp.DetailFimActivity.DetailMovieActivity
import com.example.movieapp.DetailFimActivity.FilmItem
import com.example.movieapp.GenreBottom.OnShowBottomSheet
import com.example.movieapp.LocalDatabase.LocalDAO
import com.example.movieapp.LocalDatabase.LocalDatabase
import com.example.movieapp.MainActivity.screens.SupportScreen.SupportRepository
import com.example.movieapp.MainActivity.screens.SupportScreen.SupportViewmodel
import com.example.movieapp.MainActivity.screens.SupportScreen.ViewModelFactory
import com.example.movieapp.R
import com.example.movieapp.SeeAllPackage.SeeAllActivity
import com.example.movieapp.domain.Category
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.ui.theme.MovieAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private lateinit var  supportRepository :SupportRepository
    private lateinit var supportViewmodel :SupportViewmodel
    private val broadcastReceiver = object :BroadcastReceiver(){
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == "object"){
                val filmItemModel = intent.getSerializableExtra("object",FilmItemModel::class.java)
                filmItemModel?.let {
                    supportViewmodel.convertFilmItemToFilmLocal(
                        it
                    )
                }?.let {
                    supportViewmodel.insertMovie(it)
                }
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val filter1 = IntentFilter()
        filter1.addAction("object")
        registerReceiver(broadcastReceiver,filter1, RECEIVER_NOT_EXPORTED)
        supportRepository = SupportRepository(LocalDatabase.getInstance(this).localDao())
        supportViewmodel  =ViewModelProvider(
            this,
            ViewModelFactory(supportRepository)
        )[SupportViewmodel::class.java]
        setContent {
            MovieAppTheme{
                MainScreen(supportViewmodel,::onSeeAllClick) {
                    item->
                    val intent= Intent(this, DetailMovieActivity::class.java)
                    intent.putExtra("object",item)
                    startActivity(intent)
                }
            }

        }
    }
    private fun onSeeAllClick(title: String){
        val intent = Intent(this,SeeAllActivity::class.java)
        intent.putExtra("title",title)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(supportViewmodel: SupportViewmodel,onSeeAllClick:(String)->Unit,onItemClick :(FilmItemModel)->Unit){
    val user=FirebaseAuth.getInstance().currentUser
    val navController= rememberNavController()


    Scaffold(
        modifier = Modifier.background(color = colorResource(R.color.blackBackground)).navigationBarsPadding(),
        bottomBar={BottomNavigationBar(link = user?.photoUrl.toString(),navController)
                  },
        backgroundColor = colorResource(R.color.blackBackground)
        )
    {
        paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color =colorResource(R.color.black1))
        ){

            BottomNavGraph(supportViewmodel,onSeeAllClick,navController=navController,onItemClick)
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun HomeScreen(onSeeAllClick:(String)->Unit,onItemClick: (FilmItemModel) -> Unit){
    val viewModel : MainViewModel = viewModel()

    val scaffoldState=rememberModalBottomSheetState()
    val coroutineScope= rememberCoroutineScope()

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

    val isShowBottomSheet = viewModel.isShowBottomSheet
    val tag =viewModel.tag

    val showBottomSheet:(String)->Unit={
        data->
        coroutineScope.launch {
            scaffoldState.show()
        }.invokeOnCompletion { viewModel.onOpenStatusBottomSheet(data) }
    }
    val hideBottomSheet:()->Unit = {
        coroutineScope.launch {
            scaffoldState.hide()
        }.invokeOnCompletion { viewModel.onCloseStatusBottomSheet() }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(R.color.black2))
    ) {
        ShowItemsOnScreen(showOutstandingLoading){
            MostOutStanding(showBottomSheet,viewModel,onItemClick)
        }
        SectionTitle(R.string.new_movies.toString(),true, onClick = onSeeAllClick)

        ShowItemsOnScreen(showNewMovieLoading){
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding0),
                contentPadding = PaddingValues(horizontal = MovieAppTheme.paddingDimension.padding2)
            ){
                items(newMovie){
                    item->
                    FilmItem(item,onItemClick)
                }
            }
        }
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))

        SectionTitle(R.string.upcoming_movies.toString(),true,onSeeAllClick)
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
        ShowItemsOnScreen(showUpcomingLoading){
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding0),
                contentPadding = PaddingValues(horizontal = MovieAppTheme.spacerDimension.spacer2)
            ){
                items(upcoming){
                        item->
                    FilmItem(item,onItemClick)
                }
            }
        }
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
        SectionTitle(R.string.category.toString(),false,null)
        ShowItemsOnScreen(showCategoryLoading) {
            Categories(categories)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MovieAppTheme.blockDimension.b10)
                .background(color = colorResource(R.color.black1))
        )
    }
    OnShowBottomSheet(tag,isShowBottomSheet,scaffoldState,hideBottomSheet)
}

@Composable
fun ShowItemsOnScreen(showLoading:Boolean, content:@Composable ()-> Unit){
    if(showLoading){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MovieAppTheme.blockDimension.b5),
        ){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }else{
        content()
    }
}

@Composable
fun SectionTitle(title:String, isDisplay:Boolean, onClick: ((String) -> Unit)?){
    Box(modifier = Modifier
        .padding(horizontal = MovieAppTheme.paddingDimension.padding3, vertical = MovieAppTheme.paddingDimension.padding3)
        .fillMaxWidth()){
        Text(text = title,
            style = MovieAppTheme.appTypoTheme.t20,
            modifier = Modifier.align(Alignment.TopStart)
        )
        if(isDisplay){
            Text(
                text = R.string.see_all.toString(),
                style =MovieAppTheme.appTypoTheme.t19,
                modifier = Modifier
                    .clickable {
                        if (onClick != null) {
                            onClick(title)
                        }
                    }
                    .align(Alignment.BottomEnd)
            )
        }
    }

}

@Composable
fun Tag(onOpenGenre: ((String) -> Unit)?,title:String){
    Box(
        modifier= Modifier
            .background(
                color = colorResource(R.color.black2),
                shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20)
            )
            .border(
                BorderStroke(
                    width = MovieAppTheme.dimensionValue.borderWidthForTag,
                    brush = Brush.linearGradient(colors = MovieAppTheme.colorScheme.linearGradientColorsForButton)
                ), shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20)
            )
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .clickable {
                if (onOpenGenre != null) {
                    onOpenGenre(title)
                }
            }
    ){
        Text(
            text=title,
            style=MovieAppTheme.appTypoTheme.t21,
            modifier = Modifier.padding(vertical =MovieAppTheme.paddingDimension.padding1, horizontal = MovieAppTheme.paddingDimension.padding12)
        )
    }
}

@Composable
fun MostOutStanding(onOpenGenre: (String) -> Unit,viewModel: MainViewModel,onItemClick: (FilmItemModel) -> Unit){
    val filmItemModel: FilmItemModel=viewModel.outstandingMovie[0]
    val imageOnTop = viewModel.imageOnTop
    val index = viewModel.index
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier= Modifier
                    .clickable { onItemClick(filmItemModel) }
                    .height(MovieAppTheme.blockDimension.b42)
                    .fillMaxWidth()
            ){
                AsyncImage(
                    model = imageOnTop,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier
                        .height(MovieAppTheme.blockDimension.b38)
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
                    style = MovieAppTheme.appTypoTheme.t6,
                    modifier = Modifier
                        .padding(
                            start =MovieAppTheme.paddingDimension.padding2,
                            end = MovieAppTheme.paddingDimension.padding2,
                            bottom = MovieAppTheme.paddingDimension.padding2)
                        .align(Alignment.BottomCenter)
                )

            }
            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
            ){
                for(i in 0..<filmItemModel.Genre.size){
                    item {
                        Tag(onOpenGenre,filmItemModel.Genre[i])
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
            modifier = Modifier.padding(MovieAppTheme.paddingDimension.padding2),
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
        .height(MovieAppTheme.blockDimension.b5)
        .width(MovieAppTheme.blockDimension.b9)
        .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10))
        .clickable{
            viewModel.changeImageOnTop(link,index)
        }
    ){
        AsyncImage(
            model = link,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = if(isSelected) MovieAppTheme.alpha.a10 else MovieAppTheme.alpha.a3
        )
    }
}

@Composable
fun Categories(list:List<Category>){
    LazyHorizontalGrid(
        horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
        verticalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
        contentPadding = PaddingValues( MovieAppTheme.paddingDimension.padding2),
        rows = GridCells.Fixed(2), modifier = Modifier.height(MovieAppTheme.blockDimension.b25) )
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
            .width(MovieAppTheme.blockDimension.b18)
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r10))

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
                .padding(bottom = MovieAppTheme.paddingDimension.padding2, start = MovieAppTheme.paddingDimension.padding4)
            , style = MovieAppTheme.appTypoTheme.t22
        )
    }
}


