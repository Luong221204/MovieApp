package com.example.movieapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeSlotReusePolicy
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.domain.FilmItemModel
import com.example.movieapp.screens.ProfileScreen
import com.example.movieapp.screens.SettingsScreen
import com.example.movieapp.screens.SupportScreen

@Composable
fun BottomNavGraph(navController:NavHostController, onItemClick: (FilmItemModel) -> Unit){
    NavHost(
        navController=navController,
        startDestination =BottomMenuItem.HomeScreen.route
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