package com.example.movieapp.MainActivity.screens.ExploreScreen

import com.example.movieapp.R
import com.example.movieapp.domain.Country

class FilterBottomSheetRepository {

    fun getGenreList():List<String>{
        val list= listOf(
            "Adventure","Sci-Fi","Action","Thriller","Drama","Fantasy","Comedy","Romantic"
        )
        return list
    }

    fun getCountryList():List<Country>{
        val list = listOf(
            Country("USA", R.drawable.usa),
            Country("Brit", R.drawable.british),
            Country("Bra", R.drawable.bra),
            Country("Vie", R.drawable.vie),
            Country("Spain", R.drawable.spain))
        return list
    }
}