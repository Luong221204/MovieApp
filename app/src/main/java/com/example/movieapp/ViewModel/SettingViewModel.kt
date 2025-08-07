package com.example.movieapp.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.movieapp.GenreBottom.GenreRepository
import com.example.movieapp.MainActivity.screens.ExploreScreen.ExploreRepository
import com.example.movieapp.domain.Country
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class SettingViewModel: ViewModel() {


    private val repository = ExploreRepository()

    var isShow by mutableStateOf(false)
    var text by mutableStateOf("")
    val searchFilm = repository.searchedFilms


    val filteredFilm=repository.filteredFilm

    fun open(){
        isShow=true
    }

    fun close(){
        isShow=false
    }

    fun saveFilterToRepo(filter:String,genres:List<String>,country:String,year:String){
        repository.resetFilter( filter,genres,country,year)
    }

    fun onSearchListener(data:String){
        repository.onSearchListener(data)
    }


}