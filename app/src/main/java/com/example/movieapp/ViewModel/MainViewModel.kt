package com.example.movieapp.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.FilmsLoadingResult
import com.example.movieapp.Repository.MainRepository
import com.example.movieapp.domain.FilmItemModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.Thread.State

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    var isFirstUpcomingLoading by mutableIntStateOf(0)
    var isFirstNewMovieLoading by mutableIntStateOf(0)

    //list
    val upcoming =  mutableStateListOf<FilmItemModel>()
    val newMovie =  mutableStateListOf<FilmItemModel>()

    //process loading
    var showUpcomingLoading by  mutableStateOf(true)
    var showNewMovieLoading by  mutableStateOf(true)

    //movie repo
    val loadUpcoming=repository.upcomingMovies
    val loadItems= repository.newMovies


    fun addNewMovies(list:List<FilmItemModel>){
        newMovie.clear()
        newMovie.addAll(list)
        if(list.isNotEmpty()) {
            isFirstUpcomingLoading=1
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
}