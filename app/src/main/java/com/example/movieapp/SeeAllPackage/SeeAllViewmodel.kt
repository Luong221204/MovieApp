package com.example.movieapp.SeeAllPackage

import androidx.lifecycle.ViewModel

class SeeAllViewmodel(
    private val title:String
):ViewModel() {
    private val repository = SeeAllRepository(title)

    val films = repository.films

}