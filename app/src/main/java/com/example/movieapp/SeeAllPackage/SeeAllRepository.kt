package com.example.movieapp.SeeAllPackage

import android.util.Log
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SeeAllRepository(
    private val title:String
) {
    private val firestore= FirebaseFirestore.getInstance()

    init {
        loadItems()
    }

    private var _films =MutableStateFlow<List<FilmItemModel>>(emptyList())
    var films: StateFlow<List<FilmItemModel>> = _films

    private fun loadItems(){
        firestore.collection("Movies")
            .get()
            .addOnSuccessListener { result ->
                val listFilms:ArrayList<FilmItemModel> = ArrayList()
                for (document in result) {
                    val movie = document.toObject(FilmItemModel::class.java)
                    movie.let {
                        movie.id=document.id
                        listFilms.add(movie)
                    }
                    _films.value=listFilms

                }
            }
            .addOnFailureListener { exception ->
                Log.w("DUCLUONG", "Error getting documents.", exception)
            }
    }
}