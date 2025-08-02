package com.example.movieapp.DetailFimActivity.Viewmodel

sealed class FilmsLoadingResult {
    data object OnSuccess: FilmsLoadingResult()
    data object OnLoading: FilmsLoadingResult()
}