package com.example.movieapp.GenreBottom

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class GenreRepository {

    private val firestore = FirebaseFirestore.getInstance()

    private val _searchedFilms = MutableStateFlow<List<FilmItemModel>>(emptyList())
    val searchedFilms : StateFlow<List<FilmItemModel>> = _searchedFilms

    fun init(data:String){
        firestore.collection("Movies")
            .whereArrayContains("Genre",data)
            .get()
            .addOnSuccessListener {
                    result->
                val listFilm : ArrayList<FilmItemModel> = ArrayList()
                for(document in result){
                    val movieFilter = document.toObject(FilmItemModel::class.java)
                    movieFilter.id=document.id
                    listFilm.add(movieFilter)
                }
                _searchedFilms.value=listFilm
            }
    }

    fun onSearchListener(data:String){
        val newList :ArrayList<FilmItemModel> = ArrayList()
        _searchedFilms.value.forEach {
            if(it.Title.lowercase(java.util.Locale.ENGLISH).contains(data.lowercase(java.util.Locale.ENGLISH))){
                newList.add(it)
            }
        }
        _searchedFilms.value=newList
    }

}