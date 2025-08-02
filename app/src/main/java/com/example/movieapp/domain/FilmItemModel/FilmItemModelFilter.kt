package com.example.movieapp.domain.FilmItemModel

import com.example.movieapp.domain.CastModel
import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

data class FilmItemModelFilter(
    var Title: String="",
    var Description:String="",
    var Poster: String="",
    var Time: String="",
    var Trailer: Trailer=Trailer(),
    var Imdb: Float =0f,
    var Year: Int=0,
    var price: Double=0.0,
    var Genre: ArrayList<String> =ArrayList(),
    var Casts: ArrayList<DocumentReference> =ArrayList(),
    var CastsAfterFilter: ArrayList<CastModel> =ArrayList(),
    var Gallery: ArrayList<String> =ArrayList(),
    var id:String=""
    ): Serializable