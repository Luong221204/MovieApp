package com.example.movieapp.CastPackage

import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CastViewmodel(
    private val castModel: CastModel
):ViewModel() {
    private val repository = CastRepository(castModel)

    val films =repository.films

    var cast:CastModel = repository.castModel

}