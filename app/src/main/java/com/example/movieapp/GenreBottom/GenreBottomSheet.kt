package com.example.movieapp.GenreBottom

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp.DetailFimActivity.FilmItem
import com.example.movieapp.DetailFimActivity.FilmItem2
import com.example.movieapp.DetailFimActivity.MoreLikeThis
import com.example.movieapp.DetailFimActivity.Video
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.R
import com.example.movieapp.SearchBar
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlin.properties.Delegates

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreBottomSheet(
    data:String,
    sheetState: SheetState,
    hideBottomSheet:()->Unit
){
    val viewModel:GenreViewModel = viewModel()
    viewModel.init(data)
    val listFilm = viewModel.searchedFilms.collectAsState()
    var lineState by remember{
        mutableIntStateOf(150)
    }
    val density = LocalDensity.current
    var screenWidth by Delegates.notNull<Int>()
    ModalBottomSheet(
        dragHandle = {BottomSheetDefaults.DragHandle(color = Color.Red.copy(alpha = MovieAppTheme.alpha.a8))},
        containerColor = colorResource(R.color.blackBackground),
        onDismissRequest = {
            hideBottomSheet()
        },
        sheetState = sheetState,
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.blackBackground))
                .onGloballyPositioned {
                        coordinates ->
                    screenWidth = with(density) { coordinates.size.width.toDp().value.toInt() }
                }
        ){
            Column(
            ) {
                SearchBar("",{}){
                    data->
                    viewModel.onSearchListener(data)
                }
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ){
                    Row(modifier = Modifier.padding(horizontal =MovieAppTheme.paddingDimension.padding3),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly){
                        Line(modifier = Modifier.padding(end=MovieAppTheme.paddingDimension.padding3).width(lineState.dp),lineState)
                        Text(text = data, modifier = Modifier.onGloballyPositioned {
                                layoutCoordinates ->
                            val size = layoutCoordinates.size
                            val textWidthDp = with(density) { size.width.toDp().value.toInt() }
                            lineState = (screenWidth-textWidthDp-64)/2
                        },
                            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        )
                        Line(modifier = Modifier.padding(start = MovieAppTheme.paddingDimension.padding3).width(lineState.dp),lineState)
                    }
                    SectionTitle(R.string.new_movies.toString(),true) { }
                    Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
                    ListFilm(listFilm.value)
                    Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
                    SectionTitle(R.string.upcoming_movies.toString(),true) { }
                    Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
                    ListFilm(listFilm.value)
                    Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
                    SectionTitle(R.string.watched.toString(),true) { }
                    Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
                    ListTv(listFilm.value)
                }

            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnShowBottomSheet(data: String,isShowBottomSheet:Boolean,sheetState:SheetState,hideBottomSheet:()->Unit){
    if(isShowBottomSheet){
        GenreBottomSheet(data,sheetState,hideBottomSheet)
    }
}


@Composable
fun Line(modifier: Modifier,int:Int){
    Box(
        modifier = modifier
            .height(MovieAppTheme.thinDimension.t1)
            .background(Color.Gray.copy(alpha = MovieAppTheme.alpha.a5))
    )
}

@Composable
fun ListFilm(listFilm:List<FilmItemModel>){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
        contentPadding = PaddingValues(horizontal =MovieAppTheme.paddingDimension.padding3)
    ){
        items(listFilm){
                item->
            FilmItem(item){}
        }
    }
}
@Composable
fun ListTv(listFilm:List<FilmItemModel>){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding1),
        contentPadding = PaddingValues(horizontal = MovieAppTheme.paddingDimension.padding3)
    ){
        items(listFilm){
                item->
            Movie(item){}
        }
    }
}
@Composable
fun Movie(filmItemModel: FilmItemModel,onClick:()->Unit){
    Column {
        Video(filmItemModel.Poster, modifier = Modifier)
        Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
        Text(
            text = filmItemModel.Title,
            style = MovieAppTheme.appTypoTheme.t23
        )
    }

}