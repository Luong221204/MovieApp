package com.example.movieapp.CastPackage

import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.CastModelFilter
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.internal.http2.Http2Connection

class CastRepository(
    val castModel: CastModel
) {
    private val firestore = FirebaseFirestore.getInstance()

    private val _films= MutableStateFlow<List<FilmItemModel>>(emptyList())
    val films:StateFlow<List<FilmItemModel>> = _films

    init{
        getFilms()
    }

    private fun getFilms(){
        firestore.collection("Casts")
            .document(castModel.id)
            .get()
            .addOnSuccessListener { it ->
                if(it.exists()){
                    val listFilm = it.get("Films") as List<DocumentReference>
                    val list : ArrayList<FilmItemModel> = ArrayList()
                    for(film in listFilm){
                        film.get().addOnSuccessListener {
                                result->
                            val filmItem = result.toObject(FilmItemModel::class.java)
                            filmItem?.let{
                                filmItem.id=result.id
                                list.add(filmItem)
                                _films.value=list
                            }
                        }
                    }
                }
            }
    }

}