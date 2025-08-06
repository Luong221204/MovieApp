package com.example.movieapp.MainActivity.BottomNavBar

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieapp.R

@Composable
fun BottomNavigationBar(link: String?=null,navController:NavHostController){
    var selectedItem by remember{
        mutableStateOf("Home")
    }
    val bottomMenuItemsList= prepareBottomMenu(link,selectedItem)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination =navBackStackEntry?.destination
    BottomAppBar (
        contentColor = colorResource(id = R.color.white),
        backgroundColor = colorResource(id = R.color.blackBackground),
    ){
        bottomMenuItemsList.forEachIndexed {
            index,bottomMenuItem ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any{
                    it.route == bottomMenuItem.route
                }==true,
                onClick = {
                    selectedItem=bottomMenuItem.label
                    navController.navigate(bottomMenuItem.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState=true
                        }
                        launchSingleTop=true
                        restoreState = true
                    }
                },
                icon={
                    if(bottomMenuItem.link != null){
                        AsyncImage(
                            model = link,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .customBottomItem(bottomMenuItem.isChecked(selectedItem),)
                        )
                    }else{
                        Icon(
                            painter = painterResource( bottomMenuItem.icon),
                            contentDescription = bottomMenuItem.label,
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
                        )
                    }

                },
                alwaysShowLabel = true,
                enabled = true,
                selectedContentColor = Color.Red.copy(alpha = 0.8f),
                unselectedContentColor = Color.White,
            )
        }
    }
}
sealed class BottomMenuItem(
    val label:String,
    val icon:Int,
    var link:String?=null,
    private var selectedItem :String?=null,
    val route:String,
){
    data object HomeScreen : BottomMenuItem(
        label = "Home",
        icon = R.drawable.home,
        route="home"
    )
    data object ProfileScreen: BottomMenuItem(
        label = "Profile",
        icon = R.drawable.btn_4,
        route="profile"
    )
    data object SupportScreen: BottomMenuItem(
        label = "Support",
        icon = R.drawable.list,
        route="support"
    )
    data object ExploreScreen: BottomMenuItem(
        label = "Settings",
        icon =R.drawable.btn_1,
        route="settings"
    )
    fun setSelectedItem(selectedItem: String): BottomMenuItem {
        this.selectedItem=selectedItem
        return this
    }
    fun setProfileAvatar(link:String?=null): BottomMenuItem {
        this.link=link
        return this
    }
    fun isChecked(selectedItem: String):Boolean{
        return this.label==selectedItem
    }
}



@Composable
fun prepareBottomMenu( link:String? = null,selectedItem:String= BottomMenuItem.HomeScreen.label):List<BottomMenuItem>{
    return listOf(
        BottomMenuItem.HomeScreen.setSelectedItem(selectedItem),
        BottomMenuItem.ExploreScreen.setSelectedItem(selectedItem),
        BottomMenuItem.SupportScreen.setSelectedItem(selectedItem),
        BottomMenuItem.ProfileScreen.setSelectedItem(selectedItem).setProfileAvatar(link),
    )
}


@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.customBottomItem(checked:Boolean):Modifier{
    return this.then(
        Modifier.border(
            BorderStroke(
                width = 2.dp,
                color =  when(checked){
                    true->Color.Red.copy(alpha = 0.8f)
                    false->Color.White
                }
            ),
            shape = CircleShape
        )
            .padding(2.dp)
    )
}