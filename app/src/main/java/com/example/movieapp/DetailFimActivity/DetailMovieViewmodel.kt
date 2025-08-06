package com.example.movieapp.DetailFimActivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel

class DetailMovieViewmodel( val filmItemModel: FilmItemModel) :ViewModel() {
    private val repository = DetailMovieRepository(filmItemModel)

    var isShowBottomSheet by mutableStateOf(false)
    var tag by mutableStateOf("")

    val searchedFilm = repository.searchedFilms
    var showLoading by mutableStateOf(true)

    val listCasts = repository.casts

    fun onOpenStatusBottomSheet(data:String){
        isShowBottomSheet=true
        tag=data
    }

    fun onCloseStatusBottomSheet(){
        isShowBottomSheet=false
    }
}