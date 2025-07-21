package com.example.movieapp.domain

import java.io.Serializable
import kotlinx.serialization.Serializer

data class FilmItemModel(
    var Title: String="",
    var Description:String="",
    var Poster: String="",
    var Time: String="",
    var Trailer: String="",
    var Imdb:Int=0,
    var Year: Int=0,
    var price: Double=0.0,
    var Genre: ArrayList<String> =ArrayList(),
    var Casts: ArrayList<CastModel> =ArrayList()
):Serializable