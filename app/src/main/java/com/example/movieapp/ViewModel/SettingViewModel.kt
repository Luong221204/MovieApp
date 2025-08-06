package com.example.movieapp.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movieapp.GenreBottom.GenreRepository

class SettingViewModel: ViewModel() {
    private val repository = GenreRepository()
    var text by mutableStateOf("")
    val searchFilm = repository.searchedFilms
    fun init(data:String){
        repository.init(data)
    }
}