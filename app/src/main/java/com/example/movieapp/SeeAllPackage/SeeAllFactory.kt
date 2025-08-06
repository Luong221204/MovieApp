package com.example.movieapp.SeeAllPackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SeeAllFactory(
    private val title:String
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SeeAllViewmodel::class.java)){
            return SeeAllViewmodel(title) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}