package com.example.movieapp.MainActivity.screens.ExploreScreen

import android.util.Log
import com.example.movieapp.domain.Country
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreRepository {
    private val firestore = FirebaseFirestore.getInstance()

    private val _searchedFilms = MutableStateFlow<List<FilmItemModel>>(emptyList())
    val searchedFilms : StateFlow<List<FilmItemModel>> = _searchedFilms

    private val _filteredFilm = MutableStateFlow<List<FilmItemModel>>(emptyList())
    val filteredFilm : StateFlow<List<FilmItemModel>> = _filteredFilm

    var filter:String="Movies"
    var year:String=""
    var genres = mutableListOf(
        "Adventure","Sci-Fi","Action","Thriller","Drama","Fantasy","Comedy","Romantic"
    )
    var country : String=""

    init {
        init()
    }
    fun init(){
        firestore.collection(filter)
            .get()
            .addOnSuccessListener {
                    result->
                val listFilm : ArrayList<FilmItemModel> = ArrayList()
                for(document in result){
                    val movieFilter = document.toObject(FilmItemModel::class.java)
                    movieFilter.id=document.id
                    listFilm.add(movieFilter)
                }
                _searchedFilms.value=listFilm
            }
    }

    fun onSearchListener(data:String){
        firestore.collection("Movies")
            .get()
            .addOnSuccessListener {
                    result->
                val listFilm : ArrayList<FilmItemModel> = ArrayList()
                for(document in result){
                    val movieFilter = document.toObject(FilmItemModel::class.java)
                    movieFilter.id=document.id
                    listFilm.add(movieFilter)
                }
                _filteredFilm.value=listFilm.filter {
                    it.Title.lowercase().contains(data.lowercase())
                            && checkYear(it.Year.toString())
                            && checkGenre(it.Genre)
                }
            }

    }

    fun resetFilter(f:String,g:List<String>,c:String,y:String){
        filter=f
        genres.clear();genres.addAll(g)
        country=c
        year=y
    }

    private fun checkGenre(list:List<String>):Boolean{
        var isCorrect=false
        for(i in genres){
            if(list.contains(i)){
                isCorrect=list.contains(i)
                break
            }
        }

        return isCorrect
    }

    private fun checkYear(y:String):Boolean{
        return year == "" || year == y
    }

    private fun checkCountry(c:String):Boolean{
        return country == "" || country == c
    }
}