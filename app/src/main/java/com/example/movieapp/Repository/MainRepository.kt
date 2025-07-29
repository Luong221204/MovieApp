package com.example.movieapp.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.domain.FilmItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainRepository  {
    private val firebaseDatabase=FirebaseDatabase.getInstance()

    private var _upcomingMovies =MutableStateFlow<List<FilmItemModel>>(emptyList())
    var upcomingMovies: StateFlow<List<FilmItemModel>> = _upcomingMovies

    private var _newMovies =MutableStateFlow<List<FilmItemModel>>(emptyList())
    var newMovies: StateFlow<List<FilmItemModel>> = _newMovies

    init{
        loadUpComing()
        loadItems()
    }
     private fun loadUpComing(){
         val ref = firebaseDatabase.getReference("Upcoming")
         ref.addValueEventListener(object:ValueEventListener{
             override fun onDataChange(snapshot: DataSnapshot) {
                 val lists = mutableListOf<FilmItemModel>()
                 for(childSnapShot in snapshot.children){
                     val item = childSnapShot.getValue(FilmItemModel::class.java)
                     item?.let{
                         lists.add(it)
                     }
                 }
                 _upcomingMovies.value=lists

             }

             override fun onCancelled(error: DatabaseError) {
                 TODO("Not yet implemented")
             }
         })
     }

    private fun loadItems(){
        val ref = firebaseDatabase.getReference("Items")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<FilmItemModel>()
                for(childSnapShot in snapshot.children){
                    val item = childSnapShot.getValue(FilmItemModel::class.java)
                    item?.let{
                        lists.add(it)
                    }
                }
                _newMovies.value=lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}