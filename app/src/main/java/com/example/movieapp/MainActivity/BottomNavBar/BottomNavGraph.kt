package com.example.movieapp.MainActivity.BottomNavBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.MainActivity.HomeScreen
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.MainActivity.screens.ProfileScreen
import com.example.movieapp.MainActivity.screens.SettingsScreen
import com.example.movieapp.MainActivity.screens.SupportScreen

@Composable
fun BottomNavGraph(navController:NavHostController, onItemClick: (FilmItemModel) -> Unit){
    NavHost(
        navController=navController,
        startDestination = BottomMenuItem.HomeScreen.route
    ) {
        composable(route = BottomMenuItem.HomeScreen.route){
           HomeScreen(onItemClick)
        }
        composable(route = BottomMenuItem.SupportScreen.route){
            SupportScreen()
        }
        composable(route = BottomMenuItem.SettingScreen.route){
            SettingsScreen()
        }
        composable(route = BottomMenuItem.ProfileScreen.route){
            ProfileScreen()
        }
    }
}