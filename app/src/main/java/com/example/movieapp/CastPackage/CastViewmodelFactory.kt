package com.example.movieapp.CastPackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.domain.CastModel

class CastViewmodelFactory(
    val castModel: CastModel
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CastViewmodel::class.java)){
            return CastViewmodel(castModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}