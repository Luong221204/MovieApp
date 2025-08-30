package com.example.movieapp.domain.FilmItemModel

import com.example.movieapp.domain.CastModel
import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

data class FilmItemModel(
    var Title: String="",
    var Description:String="",
    var Poster: String="",
    var Time: String="",
    var Trailer: Trailer=Trailer(),
    var Imdb: Float =0f,
    var Year: Int=0,
    var price: Double=0.0,
    var Genre: ArrayList<String> =ArrayList(),
    var Actors: ArrayList<CastModel> =ArrayList(),
    var Gallery: ArrayList<String> =ArrayList(),
    val Link:String = "",
    var id:String=""
    ):Serializable