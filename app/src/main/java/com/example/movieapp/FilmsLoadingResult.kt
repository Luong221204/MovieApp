package com.example.movieapp

sealed class FilmsLoadingResult {
    data object OnSuccess:FilmsLoadingResult()
    data object OnLoading:FilmsLoadingResult()
}