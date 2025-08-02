package com.example.movieapp.domain

import java.io.Serializable

data class CastModel(
    var PicUrl:String = "",
    var Actor:String = "",
    var Bio:String="",
    var Born:String="",
    var Nationality:String="",
    var Top:Int=-1,
    var Gallery:ArrayList<String> = ArrayList()
): Serializable
