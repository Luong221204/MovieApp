package com.example.movieapp.LocalDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalDAO {


    @Query("Select * from movies limit 10")
    fun getAllMovies(): Flow<List<FilmItemModelLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMovie(movie:FilmItemModelLocal)


}