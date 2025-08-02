package com.example.movieapp.MainActivity

import android.util.Log
import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.Category
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModelFilter
import com.example.movieapp.domain.FilmItemModel.Outstanding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainRepository  {
    private val firebaseDatabase=FirebaseDatabase.getInstance()
    private val firestore=FirebaseFirestore.getInstance()


    private var _upcomingMovies =MutableStateFlow<List<FilmItemModel>>(emptyList())
    var upcomingMovies: StateFlow<List<FilmItemModel>> = _upcomingMovies

    private var _newMovies =MutableStateFlow<List<FilmItemModel>>(emptyList())
    var newMovies: StateFlow<List<FilmItemModel>> = _newMovies

    private var _outstandingMovie =MutableStateFlow<List<FilmItemModel>>(emptyList())
    var outstandingMovie: StateFlow<List<FilmItemModel>> = _outstandingMovie

    private var _categories =MutableStateFlow<List<Category>>(emptyList())
    var categories: StateFlow<List<Category>> = _categories


    init{
        getCategories()
        loadItems()
        loadMostOutStanding()
        getUpComingMovies()
    }
    private fun loadItems(){
        firestore.collection("Movies")
            .get()
            .addOnSuccessListener { result ->
                val listFilms:ArrayList<FilmItemModel> = ArrayList()
                for (document in result) {
                    val movie = document.toObject(FilmItemModel::class.java)
                    movie.let {
                        movie.id=document.id
                        listFilms.add(movie)
                    }
                    _newMovies.value=listFilms

                }
            }
            .addOnFailureListener { exception ->
                Log.w("DUCLUONG", "Error getting documents.", exception)
            }
    }

    private fun loadMostOutStanding(){
        val lists = mutableListOf<FilmItemModel>()
        firestore.collection("Outstanding")
            .get()
            .addOnSuccessListener{
                result->
                for(document in result){
                    val out = document.toObject(Outstanding::class.java)
                    out.id?.get()?.addOnSuccessListener {
                        val movie = it.toObject(FilmItemModel::class.java)
                        if (movie != null) {
                            movie.id=document.id
                            lists.add(movie)
                        }
                            _outstandingMovie.value=lists

                        }
                    }
                }
            }

    private fun getUpComingMovies(){
        firestore.collection("Movies")
            .get()
            .addOnSuccessListener { result ->
                val listFilms:ArrayList<FilmItemModel> = ArrayList()
                for (document in result) {
                    val movie = document.toObject(FilmItemModel::class.java)
                    movie.let {
                        movie.id=document.id
                        listFilms.add(movie)
                    }
                    _upcomingMovies.value=listFilms

                }
            }
            .addOnFailureListener { exception ->
                Log.w("DUCLUONG", "Error getting documents.", exception)
            }
    }

    private fun getCategories(){
       firestore.collection("Categories")
           .get()
           .addOnSuccessListener {
               result->
               val categories:ArrayList<Category> = ArrayList()
               for(document in result){
                   val category = document.toObject(Category::class.java)
                   categories.add(category)
               }
               _categories.value=categories


           }
    }
}