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
                    val film =FilmItemModel()
                    val movieFilter = document.toObject(FilmItemModelFilter::class.java)
                    val listCast : ArrayList<CastModel> = ArrayList()
                    for(castRef in movieFilter.Casts){
                        castRef.get().addOnSuccessListener {
                            val cast = it.toObject(CastModel::class.java)
                            cast?.let{
                                listCast.add(cast)
                            }
                        }
                    }
                    film.apply{
                        Title=movieFilter.Title
                        Time=movieFilter.Time
                        Description=movieFilter.Description
                        Casts.addAll(movieFilter.CastsAfterFilter)
                        this.Genre=movieFilter.Genre
                        this.Imdb=movieFilter.Imdb
                        this.Poster=movieFilter.Poster
                        this.Year=movieFilter.Year
                        this.Gallery=movieFilter.Gallery
                        this.Trailer=movieFilter.Trailer
                    }
                    listFilm.add(film)
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