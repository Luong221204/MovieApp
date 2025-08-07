package com.example.movieapp.LocalDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.domain.FilmItemModel.Trailer


@Entity(tableName = "movies")
data class FilmItemModelLocal(
    @PrimaryKey
    var id :String ="",
    var Title: String="",
    var Description:String="",
    var Poster: String="",
    var Time: String="",
    var Imdb: Float =0f,
    var Year: Int=0,
)
