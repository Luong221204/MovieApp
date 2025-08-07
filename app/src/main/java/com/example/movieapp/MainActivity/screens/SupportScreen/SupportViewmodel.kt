package com.example.movieapp.MainActivity.screens.SupportScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.LocalDatabase.FilmItemModelLocal
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SupportViewmodel(
    private val repository: SupportRepository
):ViewModel() {

    val history = repository.getHistory.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    suspend fun insertMovie(movie:FilmItemModelLocal){
        viewModelScope.launch {
            repository.insertMovie(movie)
        }
    }


    fun convertListFilm(list:List<FilmItemModelLocal>):List<FilmItemModel>{
        return list.map {
            convertFilmLocalToFilmItem(it)
        }
    }
    private fun convertFilmLocalToFilmItem(filmItemModelLocal: FilmItemModelLocal): FilmItemModel {
        return filmItemModelLocal.let {
            FilmItemModel(Title = it.Title,
                Description = it.Description,
                Poster = it.Poster,
                Time = it.Time,
                Imdb = it.Imdb)
        }
    }

    fun convertFilmItemToFilmLocal(filmItemModel: FilmItemModel): FilmItemModelLocal {
        return filmItemModel.let {
            FilmItemModelLocal(Title = it.Title,
                Description = it.Description,
                Poster = it.Poster,
                Time = it.Time,
                Imdb = it.Imdb)
        }
    }
}