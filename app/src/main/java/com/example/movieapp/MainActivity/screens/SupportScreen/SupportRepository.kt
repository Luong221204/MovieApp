package com.example.movieapp.MainActivity.screens.SupportScreen

import com.example.movieapp.LocalDatabase.FilmItemModelLocal
import com.example.movieapp.LocalDatabase.LocalDAO
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn

class SupportRepository(
    private val localDAO: LocalDAO
) {

    val getHistory:Flow<List<FilmItemModelLocal>> = localDAO.getAllMovies()

    suspend fun insertMovie(modelLocal: FilmItemModelLocal) = localDAO.insertNewMovie(modelLocal)


}