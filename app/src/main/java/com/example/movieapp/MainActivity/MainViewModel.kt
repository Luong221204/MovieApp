package com.example.movieapp.MainActivity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.Category
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    var isShowBottomSheet by mutableStateOf(false)
    var isFirstUpcomingLoading by mutableIntStateOf(0)
    var isFirstNewMovieLoading by mutableIntStateOf(0)
    var isFirstOutstandingMovieLoading by mutableIntStateOf(0)
    var isFirstCategoryLoading by mutableIntStateOf(0)
    var imageOnTop by mutableStateOf("")
    var index by mutableIntStateOf(0)
    var tag by mutableStateOf("")

    //list
    val upcoming =  mutableStateListOf<FilmItemModel>()
    val newMovie =  mutableStateListOf<FilmItemModel>()
    val outstandingMovie = mutableStateListOf<FilmItemModel>()
    val category = mutableStateListOf<Category>()

    //process loading
    var showUpcomingLoading by  mutableStateOf(true)
    var showNewMovieLoading by  mutableStateOf(true)
    var showOutstandingLoading by  mutableStateOf(true)
    var showCategoryLoading by  mutableStateOf(true)

    //movie repo
    val loadUpcoming=repository.newMovies
    val loadItems= repository.newMovies
    val loadOutstandingMovie=repository.outstandingMovie
    val loadCategory=repository.categories




    fun addNewMovies(list:List<FilmItemModel>){
        newMovie.clear()
        newMovie.addAll(list)
        if(list.isNotEmpty()) {
            isFirstNewMovieLoading=1
            showNewMovieLoading=false
        }
    }

    fun addUpcomingMovies(list:List<FilmItemModel>){
        upcoming.clear()
        upcoming.addAll(list)
        if(list.isNotEmpty()) {
            isFirstUpcomingLoading=1
            showUpcomingLoading=false
        }
    }

    fun addOutstandingMovie(list:List<FilmItemModel>){
        outstandingMovie.clear()
        outstandingMovie.addAll(list)
        if(list.isNotEmpty()) {
            if(isFirstOutstandingMovieLoading!=1){
                imageOnTop=list[0].Poster
                index=0
            }
            isFirstOutstandingMovieLoading=1
            showOutstandingLoading=false
        }
    }


    fun addCategories(list:List<Category>){
        category.clear()
        category.addAll(list)
        if(list.isNotEmpty()){
            showCategoryLoading=false
            isFirstCategoryLoading=1
        }

    }
    fun changeImageOnTop(link:String,i:Int){
        imageOnTop=link
        index=i
    }

    fun onOpenStatusBottomSheet(data:String){
        isShowBottomSheet=true
        tag=data
    }

    fun onCloseStatusBottomSheet(){
        isShowBottomSheet=false
    }

}