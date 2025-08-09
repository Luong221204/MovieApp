package com.example.movieapp.DetailFimActivity

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModelFilter
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailMovieRepository(private val filmItemModel: FilmItemModel) {
    private val firestore=FirebaseFirestore.getInstance()

    private val _searchedFilms = MutableStateFlow<List<FilmItemModel>>(emptyList())
    val searchedFilms : StateFlow<List<FilmItemModel>> = _searchedFilms

    private val _casts = MutableStateFlow<List<CastModel>>(emptyList())
    val casts : StateFlow<List<CastModel>> = _casts


    init {
        getMoreFilms()
        getCasts()
    }

    private fun getMoreFilms(){
        firestore.collection("Movies")
            .whereArrayContains("Genre",filmItemModel.Genre[0])
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

    private fun getCasts(){
        firestore.collection("Movies")
            .document(filmItemModel.id)
            .get()
            .addOnSuccessListener {
                document->
                if(document.exists()){
                    val list:ArrayList<CastModel> = ArrayList()
                    val Casts=document.get("Casts") as? List<DocumentReference>
                    if (Casts != null) {
                        for(cast in Casts){
                            cast.get().addOnSuccessListener {
                                result->
                                val actor = result.toObject(CastModel::class.java)
                                if (actor != null) {
                                    actor.id=result.id
                                    list.add(actor)
                                }
                                _casts.value=list
                            }
                        }
                    }
                }
            }
    }
}