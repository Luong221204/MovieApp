package com.example.movieapp.domain.FilmItemModel

import com.google.firebase.firestore.DocumentReference
import java.io.Serializable

data class Outstanding(
    val id : DocumentReference?=null
): Serializable
