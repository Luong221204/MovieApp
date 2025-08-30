package com.example.movieapp.CastPackage

import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CastViewmodel(
    private val repository: CastRepository
):ViewModel() {

    val films =repository.films

    var cast:CastModel = repository.castModel

}