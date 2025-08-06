package com.example.movieapp.MainActivity.screens.ExploreScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.DetailFimActivity.FilmItem
import com.example.movieapp.DetailFimActivity.FilmItem2
import com.example.movieapp.GenreBottom.Movie
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.R
import com.example.movieapp.SearchBar
import com.example.movieapp.ViewModel.SettingViewModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val viewModel:SettingViewModel= viewModel()
    viewModel.init("Action")
    val searchFilms = viewModel.searchFilm.collectAsState()
    FilterBottomSheet({},sheetState)
    Box(
        modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.blackBackground))
    ){
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(54.dp))

            SearchBar(onFilterClick = {
                scope.launch {
                    sheetState.show()
                }
            }) {  }

            SectionTitle("Recommendation for you",false) { }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ){
                items(searchFilms.value){
                        item->
                    FilmItem(item){}
                }
            }
            SectionTitle("Tv",false) { }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchFilms.value){
                    Movie(it) { }
                }
            }
            SectionTitle("Popular",false) { }
            searchFilms.value.CustomForEach(2){
                list->
                ItemsInRow(list)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Box(modifier = Modifier.height(100.dp).background(color = colorResource(R.color.blackBackground)))
        }
    }
}


@Composable
fun ItemsInRow(list: List<FilmItemModel>){
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ){
        repeat(list.size){
            FilmItem2(list[it]) { }
            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}

@Composable
fun <T> List<T>.CustomForEach(numberInRow:Int,emit:@Composable (List<T>)->Unit){
    val limit = if(this.size % numberInRow ==0) this.size/numberInRow else this.size/numberInRow+1
    var i = 0
    while(i<limit){
        val list : ArrayList<T> = ArrayList()
        for(j in 0..<numberInRow){
            if(numberInRow*i+j<this.size){
                list.add(this[numberInRow*i+j])
            }
        }
        emit(list)
        i++
    }
}

