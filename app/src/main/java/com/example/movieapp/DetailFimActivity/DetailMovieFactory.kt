package com.example.movieapp.DetailFimActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.domain.FilmItemModel.FilmItemModel

class DetailMovieFactory(
    private val repository: DetailMovieRepository
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailMovieViewmodel::class.java)){
            return DetailMovieViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}