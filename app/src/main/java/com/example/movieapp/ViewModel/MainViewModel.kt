package com.example.movieapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.Repository.MainRepository
import com.example.movieapp.domain.FilmItemModel

class MainViewModel : ViewModel() {
    private val repository = MainRepository()
     fun loadUpcoming(): LiveData<MutableList<FilmItemModel>> {
         return repository.loadUpComing()
     }
    fun loadItems():LiveData<MutableList<FilmItemModel>>{
        return repository.loadItems()
    }
}