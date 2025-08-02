package com.example.movieapp.domain

import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.google.firebase.firestore.DocumentReference
import java.io.Serializable


data class CastModelFilter(
    var PicUrl:String = "",
    var Actor:String = "",
    var Bio:String="",
    var Born:String="",
    var Nationality:String="",
    var Top:Int=-1,
    var Gallery:ArrayList<String> = ArrayList(),
    var Films:ArrayList<DocumentReference> = ArrayList(),
    var id:String=""
): Serializable