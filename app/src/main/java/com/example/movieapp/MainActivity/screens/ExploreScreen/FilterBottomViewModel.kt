package com.example.movieapp.MainActivity.screens.ExploreScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FilterBottomViewModel:ViewModel() {
    val repository=FilterBottomSheetRepository()

    val listCountry=repository.getCountryList()
    val listGenre=repository.getGenreList()

    var movieOption by mutableStateOf(false)
    var cartoonOption by mutableStateOf(false)
    var tvOption by mutableStateOf(false)
    var seriesOption by mutableStateOf(false)
    var isShow by mutableStateOf(false)
    var currentFilter by mutableStateOf("")
    var yearSelected by mutableStateOf("--/--")
    var countryState by mutableStateOf("--/--")
    var sortState by   mutableStateOf("Recommendation")

    fun changeOption(option:String){
        when(option){
            "Movies"->{
                movieOption=true
                cartoonOption=false
                tvOption=false
                seriesOption=false
            }
            "Tv"->{
                movieOption=false
                cartoonOption=false
                tvOption=true
                seriesOption=false
            }
            "Cartoons"->{
                movieOption=false
                cartoonOption=true
                tvOption=false
                seriesOption=false
            }
            "Series"->{
                movieOption=false
                cartoonOption=false
                tvOption=false
                seriesOption=true
            }
        }
    }

    fun optionFilter(nameFilter:String,data:String){
        if(nameFilter == "Release") yearSelected= data
        else if(nameFilter == "Country") countryState= data
        else sortState=data
    }

    fun open(nameFilter:String){
        isShow=true
        currentFilter=nameFilter

    }
    fun close(nameFilter:String, data:String){
        isShow=false
        optionFilter(nameFilter,data)

    }
}