package com.example.movieapp.GenreBottom

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GenreViewModel:ViewModel() {

    private val repository = GenreRepository()
    val searchedFilms =repository.searchedFilms


    fun init(data:String){
        repository.init(data)
    }

    fun onSearchListener(data:String){
        repository.onSearchListener(data)
    }
}