package com.example.movieapp.MainActivity.screens.SupportScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(
    private val repository: SupportRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SupportViewmodel::class.java)){
            return SupportViewmodel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}